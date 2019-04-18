// components/comments/index.js
import { config } from "../../config";
import { HTTP } from "../../utils/http";
import { formatTime } from "../../utils/util"
const http = new HTTP();
const app = getApp();
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    author: Array,
    movieid: Number
  },

  /**
   * 组件的初始数据
   */
  data: {
    isShow: false,
    authors: [],
    newAuthor: [],
    value: "",
    // isClear: false,
    onfocus: false,
    needMore: false,
    needMoreClicked: false
  },

  attached() {
    var author = this.properties.author;
    if (author && author.length > 5) {
      this.setData({
        authors: author.slice(0, 5),
        needMore: true
      })
    } else {
      this.setData({
        authors: author
      })
    }
  },
  /**
   * 组件的方法列表
   */
  methods: {
    // 更多评论点击事件
    needMoreClick() {
      // console.log('s', this.properties.author,'sss',this.properties.author.concat(...this.data.newAuthor))
      console.error('newAuthor', this.data.newAuthor);
      this.setData({
        authors: this.properties.author.concat(...this.data.newAuthor),
        needMore: false,
        needMoreClicked: true
      })
    },
    // 收起更多点击事件
    retractMoreClick() {
      this.setData({
        authors: this.properties.author.slice(0, 5),
        needMore: true,
        needMoreClicked: false
      })
    },
    //点击写评论事件
    commentClick() {
      var uid = app.globalData.openid;
      var userInfo = app.globalData.userInfo;
      if (!uid || !userInfo) {
        this.triggerEvent('logined')
      } else {
        this.setData({
          isShow: true,
          onfocus: true
        })
      }
    },
    // 取消输入事件
    cancelClick() {
      this.setData({
        isShow: false,
        value: "",
        // isClear: false
      })
    },
    // 评论事件
    formSubmit(e) {
      this.tipsWords(e)
    },
    onConfirm(e) {
      this.tipsWords(e)
    },
    // clearClick() {
    //   this.setData({
    //     value: "",
    //     isClear: false
    //   })
    // },
    onInput(e) {
      var value = e.detail.value;
      this.setData({
        value
      })
    },
    // onBlur() {
    //   var str = this.data.value;
    //   var value = str.replace(/^s*|s*$/g, "");//第一个正则排除掉所有首尾可能为空格的字符串
    //   if (value && value.replace(/\s*/g, "")) {
    //     this.setData({
    //       isClear: true
    //     })
    //   }
    // },
    tipsWords: function (e) {
      const _this = this;
      if (e.detail.value.input || e.detail.value.input == "")
        var str = e.detail.value.input;
      else
        var str = e.detail.value;
      if (str) {
        var uid = app.globalData.openid;
        var id = this.properties.movieid
        var userInfo = app.globalData.userInfo;
        var date = formatTime(new Date(), true);
        let dates = new Date();
        let hour = dates.getHours()<10?`0${dates.getHours()}`:dates.getHours();
        let minute = dates.getMinutes();
        let StringDate = `今天${hour}:${minute}`;
        var value = str.replace(/^s*|s*$/g, "");//第一个正则排除掉所有首尾可能为空格的字符串
        if (value && value.replace(/\s*/g, "")) {
          // console.error('value',value)
          http.request({
            url: 'user/addcomments',
            data: {
              content: value,
              id: id,
              uid: uid,
              dates: date
            },
            method: 'POST',
          }).then((res) => {
            if (res.flag == "1") {
              var newAuthor = [..._this.data.newAuthor, { name: userInfo.nickName, avatar: userInfo.avatarUrl, content: value, date: StringDate }];
              if (this.properties.author.length > 5 && this.data.needMore) {
                this.setData({
                  value: '',
                  // isClear: false,
                  onfocus: true,
                  newAuthor: newAuthor
                }, () => wx.showToast({ title: "评论添加成功" }))
              }
              else {
                this.setData({
                  value: '',
                  // isClear: false,
                  onfocus: true,
                  newAuthor: newAuthor,
                  authors: [..._this.data.authors, { name: userInfo.nickName, avatar: userInfo.avatarUrl, content: value, date: StringDate }]
                }, () => wx.showToast({ title: "评论添加成功" }))
              }
            }
          })
        } else {
          wx.showToast({
            title: "请输入评论内容",
            icon: "none",
            success: function () {
              _this.setData({ onfocus: true })
            }
          })
        }
      } else {
        wx.showToast({
          title: "请输入评论内容",
          icon: "none",
          success: function () {
            _this.setData({ onfocus: true })
          }
        })
      }
    }
  }
})
