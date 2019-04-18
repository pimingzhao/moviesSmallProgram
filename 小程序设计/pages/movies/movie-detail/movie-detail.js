// pages/movies/movie-detail/movie-detail.js;
import { HTTP } from "../../../utils/http";
import { direct,actor,comment,star } from "../../../utils/util";
const http = new HTTP();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    isLoading: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var _this=this;
    wx.setNavigationBarTitle({
      title: '电影详情'
    })
    var id = options.id;
    http.request({
      url: `movies/detail?id=${id}`
    }).then(res=>{
      console.log('res',res)
      var subjects = {
        id: id,
        title: res.data[0].title,
        image: res.data[0].image,
        director: res.data[0].directors,
        actors: res.data[0].actors,
        collect_count: res.data[0].collect_count,
        comments: comment(res.data[0].comments),
        average: res.data[0].average,
        stars: star(res.data[0].average),
        pubdate: res.data[0].pubdate,
        summary: res.data[0].summary,
        type: res.data[0].type,
        movies_type: res.data[0].movies_type,
        language: res.data[0].language
      }
      this.setData({
        subjects: subjects
      },()=>{
        setTimeout(function(){
          _this.setData({
            isLoading: false
          })
        },300)
      })
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})