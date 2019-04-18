// pages/movies/movies.js
import { MovieModel } from "../../modles/movies";
import { sliceName,star } from "../../utils/util";
const movieModel = new MovieModel();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id:  1234,
    imgUrl: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    const inTheaters = movieModel.getInTheaters()
    const top250 = movieModel.getTop250();
    const comingSoon = movieModel.getComingSoon();
    Promise.all([inTheaters, top250, comingSoon]).then(res => {
      let [inTheaters, top250, comingSoon] = res;
      var obj = {};
      obj['in_theaters'] = {
        subjects: this.handleData(inTheaters.data.slice(0,6))
      };
      obj['top250'] = {
        subjects: this.handleData(top250.data.slice(0,6))
      };
      obj['coming_soon'] = {
        subjects: this.handleData(comingSoon.data.slice(0,6))
      };
      this.setData(obj);
      var imgUrl = [];
      inTheaters.data.slice(0,5).forEach((ele,index)=>{
        var temp = {
          image: ele.image,
          id: ele.id,
          title: ele.title
        }
        imgUrl.push(temp);
      })
      this.setData({
        imgUrl: imgUrl
      })
    })
    wx.setNavigationBarTitle({
      title: '电影列表',
    });
  },

  handleData: function(arr){
    var subjects = [];
    arr.forEach(ele => {
      var temp={
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
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  onSlideClick: function(e){
    var id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/movies/movie-detail/movie-detail?id='+id,
    })
  }
})