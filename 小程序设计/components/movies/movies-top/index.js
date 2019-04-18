// components/movies/movies-top/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    title: String,
    type: String
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    moreClick(){
      var type = this.properties.type;
      var navTitle = this.properties.title;
      wx.navigateTo({
        url: `/pages/movies/movie-more/movie-more?type=${type}&navTitle=${navTitle}`
      })
    }
  }
})
