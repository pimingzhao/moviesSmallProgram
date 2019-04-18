// pages/addMovies/addMovies.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    value: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  radioChange: function (e) {
    var value = e.detail.value
    this.setData({
      type: value
    })
  },
  titleValue: function (e) {
    var value = e.detail.value
    this.setData({
      title: value
    })
  },
  averageValue: function (e) {
    var value = e.detail.value
    this.setData({
      average: value
    })
  },
  imageValue: function (e) {
    var value = e.detail.value
    this.setData({
      image: value
    })
  },
  pubdateValue: function (e) {
    var value = e.detail.value
    this.setData({
      pubdate: value
    })
  },
  yearValue: function (e) {
    var value = e.detail.value
    this.setData({
      year: value
    })
  },
  directorValue: function (e) {
    var value = e.detail.value
    this.setData({
      director: value
    })
  },
  summaryValue: function (e) {
    var value = e.detail.value
    this.setData({
      summary: value
    })
  },
  actorsValue: function(e) {
    var value = e.detail.value
    this.setData({
      actors: value
    })
  },
  addMovies: function () {
    let { title, average, image, pubdate, year, director, summary, type, actors } = this.data;
    var obj = {
      title: title,
      average: average,
      image: image,
      pubdate: pubdate,
      year: year,
      directors: director,
      summary: summary,
      type: type,
      actors: actors
    }
    console.error('obj',obj)
    var _this = this;
    wx.request({
      method: 'POST',
      url: 'http://192.168.56.1:8082/movies/add',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      data: obj,
      success: function (res) {
        _this.setData({
          value: ''
        })
        console.error('data', res)
      },
      fail: function (res) {
        console.error('erroreData', res)
      }
    })
  }
})