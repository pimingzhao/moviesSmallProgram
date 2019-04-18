// pages/search/search.js
import { HTTP } from "../../utils/http";
import { star, actor } from "../../utils/util";
const http = new HTTP();
var arr = [];
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isClear: false,
    value: "",
    isSearch: false,
    subjects: [],
    historyTitle: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.setNavigationBarTitle({
      title: '搜索电影',
    });
  },

  formSubmit: function (e) {
    var value = e.detail.value.input;
    this.handleData(value);
    this.setClear();
  },
  onConfirm: function (e) {
    var value = e.detail.value;
    this.handleData(value);
    this.setClear();
  },
  clearClick: function () {
    this.setData({
      value: "",
      isClear: false
    })
  },
  searchClick: function () {
    var value = this.data.value;
    this.handleData(value);
    this.setClear();
  },
  onInput: function (e) {
    var value = e.detail.value;
    this.setData({
      value
    })
    if(!value) {
      if(this.data.isClear){
        this.setData({
          isClear: false
        })
      }
    }
  },
  onBlur:function(){
    var value = this.data.value;
    if(value){
      this.setData({
        isClear: true
      })
    }
  },
  // 处理输入框获取的值，放入缓存并传给子组件
  handleData(str) {
    var history = wx.getStorageSync('search-history') ? wx.getStorageSync('search-history') : [];
    var value = str.replace(/^s*|s*$/g,"");//第一个正则排除掉所有首尾可能为空格的字符串
    if (value && value.replace(/\s*/g,"")) {//这个正则判断是否整个字符串都为空格
      var flag = true;
      history.forEach((ele,index)=>{
        if(ele && ele.title == value){
          flag = false
        }
      })
      if(flag){
        if(history.length>5) {
          history.pop();
        }
        arr.unshift({ title: value });
        history.unshift({ title: value });
        this.setData({ historyTitle: arr });
        wx.setStorageSync('search-history', history)
      }
      http.request({
        url: `/movies/query?title=${value}`
      }).then(res => {
        var subjects = [];
        if(res && res.data) {
          res.data.forEach(ele => {
            var temp = {
              title: ele.title,
              image: ele.image,
              id: ele.id,
              average: ele.average,
              stars: star(ele.average),
              directors: ele.directors,
              actors: ele.actors
            }
            subjects.push(temp)
          });
          if(subjects.length!=0) {
            this.setData({
              subjects,
              isSearch: true
            })
          } else {
            wx.showToast({
              title: '暂未搜到任何信息',
              icon: 'none'
            })
          }
        }
      })
    } else {
      wx.showToast({
        title: '请输入搜索内容',
        icon: 'none'
      })
    }
  },
  setClear: function () {
    var value = this.data.value;
    if (value) {
      this.setData({
        isClear: true
      })
    } else {
      if(this.data.isClear){
        this.setData({
          isClear: false
        })
      }
    }
  },
  // 从子组件获取点击的搜索标题。将其放入输入框内
  OnSearchTitle: function (e) {
    var value = e.detail;
    this.setData({
      value: value
    })
  },
  // 从子组件获取是否需要清空当前的搜索栏
  isCleared: function(){
    arr=[];
  },
  onReachBottom: function () {
    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})