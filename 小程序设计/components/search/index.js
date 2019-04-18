// components/search/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    subjects: Object
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
    searchClick(){
      var id = this.properties.subjects.id;
      wx.navigateTo({
        url: '/pages/movies/movie-detail/movie-detail?id=' + id
      })
    }
  }
})
