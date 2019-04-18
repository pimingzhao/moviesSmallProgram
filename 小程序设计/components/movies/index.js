// components/movies/index.js
import {HTTP} from '../../utils/http'
const http = new HTTP();
const app = getApp();
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    subjects: Object,
    longTap: {
      value: false,
      type: Boolean
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    cancelCet: false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    detailClick(){
      var id = this.properties.subjects.id;
      wx.navigateTo({
        url: '/pages/movies/movie-detail/movie-detail?id='+id,
      })
    },
    longClick(e){
      this.setData({
        cancelCet: true
      })
    },
    commonClick(){
      this.detailClick();
    },
    deleteClick(e){
      var id = e.currentTarget.dataset.id;
      var uid = app.globalData.openid;
      http.request({
        url: 'user/cancelcollect',
        data: {
          id: id,
          uid: uid
        },
        method: 'POST'
      }).then((res)=>{
        if(res.flag!="3"){
          app.globalData.myUpdate=true;
          wx.showToast({title:'已移除收藏'});
          this.triggerEvent('updates');
        }
      })
    },
    cancelClick(){
      this.setData({
        cancelCet: false
      })
    }
  },

})
