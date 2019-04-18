// pages/mine/mine.js
import { HTTP } from "../../utils/http";
import { direct, actor, comment, star, formatTime } from "../../utils/util";
import { config } from '../../config';
const http = new HTTP();
//获取应用实例
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    bgImg: '/images/my/bg-img.jpg',
    avater: '/images/my/my-gray.png',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    listValue: '',
    toDoChecked: false,
    addNewTask: false,
    hasDone: false,
    hasDoneTask: [],
    subjects: [],
    toDoFocus: false,
    lastTapTime: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.setNavigationBarTitle({
      title: '我的',
    })

    var taskHistory = []
    // 获取缓存中的任务列表
    if (wx.getStorageSync('task-history'))
      taskHistory = wx.getStorageSync('task-history')
    else
      wx.setStorageSync('task-history', [])
    this.setData({
      hasDoneTask: taskHistory
    })
    //获取当前用户的id，调用后台接口，获取当前收藏的电影的id
    var uid = app.globalData.openid;
    if (uid && app.globalData.userInfo) {
      this.handleData(app.globalData.userInfo, uid);
    }

  },
  getUserInfo: function (e) {
    if (e.detail.userInfo) {
      var userInfo = e.detail.userInfo;
      app.globalData.userInfo = userInfo
      this.setData({
        userInfo: userInfo,
        hasUserInfo: true
      })
      var _this = this;
      wx.login({
        success: function (res) {
          var code = res.code;
          wx.request({
            url: config.wx_api_url,
            data: {
              appid: 'wx9e60db5e0751205d',
              secret: '6e7b23acb010ddd15a97d425076e3eae',
              js_code: code,
              grant_type: 'authorization_code'
            },
            method: 'GET',
            success: function (res) {
              wx.showToast({title: '登录成功'})
              // openid为微信用户的唯一标识
              var uid = res.data.openid;
              app.globalData.openid = uid;
              // 完成用户注册：将opendid放入数据库，添加该用户
              _this.handleData(userInfo, uid);
            },
            fail: function () {
              // fail
            }
          })
        },
        fail: function (res) {
          console.log('登录失败！' + res.errMsg)
        }
      })
    } else {
      wx.showToast({
        title: '网络连接错误,请检查你的网络',
        icon: 'none',
        success: function () {
          return false;
        }
      })
    }
  },
  handleData(userInfo, uid) {
    var _this = this;
    http.request({
      url: 'user/login',
      data: {
        name: userInfo.nickName,
        avatar: userInfo.avatarUrl,
        uid: uid
      },
      method: 'POST',
    }).then((res) => {
      if (res.flag == "2") {
        wx.request({
          url: config.base_user_url + 'query?uid=' + uid,
          method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
          success: function (res) {
            _this.handleResult(res.data)
          },
          fail: function () {
            // fail
          }
        })
      }
    })
  },
  handleResult(data) {
    var _this = this;
    var dataId = data.data[0].collection;
    var subjects = [];
    var count = 0;
    if (dataId && dataId.length != 0) {
      dataId.forEach((ele, index) => {
        http.request({
          url: `movies/detail?id=${ele.id}`
        }).then(res => {
          var temp = {
            id: res.data[0].id,
            title: res.data[0].title,
            image: res.data[0].image,
            director: res.data[0].directors,
            actors: res.data[0].actors,
            collect_count: res.data[0].collect_count,
            comments: res.data[0].comments,
            average: res.data[0].average,
            star: star(res.data[0].average),
            pubdate: res.data[0].pubdate,
            summary: res.data[0].summary,
            year: res.data[0].year,
          }
          count += 1;
          subjects.unshift(temp);
          if (count == dataId.length) {
            _this.setData({
              subjects: subjects
            })
          }
        })
      })
    } else {
      this.setData({ subjects: [] })
    }
  },
  taskListEvent: function (e) {
    var str = e.detail.value;
    var value = str.replace(/^s*|s*$/g, "");
    if (value && value.replace(/\s*/g, "")) {
      this.setData({
        listValue: value
      })
    }
  },
  onConfirm: function (e) {
    this.taskListEvent(e)
  },
  onBlur: function (e) {
    this.taskListEvent(e)
  },
  checkboxChange: function (e) {
    this.setData({
      toDoChecked: true
    })
    var addNewTask = this.data.addNewTask;
    if (!addNewTask) {
      var value = e.detail.value[0];
      var taskHistory = wx.getStorageSync('task-history') ? wx.getStorageSync('task-history') : [];
      taskHistory.unshift({
        value: value,
        time: formatTime(new Date())
      })
      wx.setStorageSync('task-history', taskHistory)
      this.setData({
        addNewTask: true,
        hasDoneTask: taskHistory
      })
    }
  },
  toDoCancel: function () {
    this.setData({
      listValue: '',
      toDoFocus: true
    })
  },
  addNewTask: function () {
    this.setData({
      toDoChecked: false,
      addNewTask: false,
      listValue: '',
      toDoFocus: true
    })
  },
  hasDoneEvent: function () {
    this.setData({
      hasDone: !this.data.hasDone
    })
  },
  clearFinished: function () {
    const _this = this;
    wx.removeStorage({
      key: 'task-history',
      success(res) {
        _this.setData({
          hasDoneTask: []
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    //获取当前用户的id，调用后台接口，获取当前收藏的电影的id
    var needUpdate = app.globalData.myUpdate;
    if (needUpdate) {
      var uid = app.globalData.openid;
      http.request({
        url: 'user/query?uid=' + uid,
      }).then((res) => {
        this.handleResult(res)
      })
      app.globalData.myUpdate=false;
    }
  },
  updatePage(){
    this.onShow();
  }
})