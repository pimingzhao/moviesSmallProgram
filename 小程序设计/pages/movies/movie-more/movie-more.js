// pages/movies/movie-more/movie-more.js
import { HTTP } from "../../../utils/http";
import { sliceName, star } from "../../../utils/util";
const http = new HTTP();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    loading: true,
    start: 1,
    isLoading: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var _this=this;
    var type = options.type;
    this.setData({
      type
    })
    var navTitle = options.navTitle;
    wx.setNavigationBarTitle({
      title: navTitle
    })
    http.request({
      url: 'movies/'+type+'/query'
    }).then(res => {
      var subjects = this.handleData(res);
      this.setData({
        subjects
      },()=>{
        setTimeout(function(){
          _this.setData({
            loading: false
          })
        },300)
      })
    })
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var _this=this;
    var start = this.data.start;
    start += 10;
    this.setData({
      isLoading: true,
      start: start
    })
    var type = this.data.type;
    http.request({
      data: {
        start: start,
        length: 10
      },
      url: `movies/${type}/query`
    }).then(res => {
      var newArr = this.handleData(res);
      if (newArr.length == 0) {
        wx.showToast({
          title: "我也是有底线的"
        })
      } else {
        var oldArr = this.data.subjects;
        var subjects = oldArr.concat(newArr);
        this.setData({
          subjects
        })
      }
      setTimeout(function(){
        _this.setData({
          isLoading: false
        })
      },300)
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  handleData: function (res) {
    var arr = res.data;
    var subjects = [];
    arr.forEach(ele => {
      var temp = {
        image: ele.image,
        id: ele.id,
        title: sliceName(ele.title),
        average: ele.average,
        collect_count: ele.collect_count,
        star: star(ele.average),
        year: ele.year
      }
      subjects.push(temp)
    });
    return subjects
  }
})