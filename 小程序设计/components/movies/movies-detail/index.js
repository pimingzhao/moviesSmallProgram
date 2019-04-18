// components/movies/movies-detail/index.js
import { config } from "../../../config";
import {HTTP} from "../../../utils/http"
const http = new HTTP();
//获取应用实例
const app = getApp()
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
    _num: 2,
    collect_count: 0,
    isLogin: false,
    collected: false
  },
  attached() {
    var collect_count = this.properties.subjects.collect_count;
    this.setData({collect_count: collect_count})
    var length = this.properties.subjects.summary.length;
    if (length > 120) {
      this.setData({
        _num: 1,
      })
    } else {
      this.setData({
        _num: 3
      })
    }
    // 查询该影片是否已被用户收藏
    var uid = app.globalData.openid;
    if(uid){
      var _this = this;
      wx.request({
        url: config.base_user_url+'query?uid='+uid,
        data: {},
        method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
        success: function(res){
          if(res.data.flag=="1"){
            res.data.data[0].collection.forEach((ele)=>{
              if(ele.id==_this.properties.subjects.id)
                _this.setData({collected:true})
            })
          }
        },
        fail: function() {
          // fail
        }
      })
    }
  },
  /**
   * 组件的方法列表
   */
  methods: {
    collectEvent(param){
      const {e,url,handleData} = param
      var userId = app.globalData.openid;
      var userInfo = app.globalData.userInfo;
      var id=e.currentTarget.dataset.id;
      if (userId && userInfo) {
        http.request({
          url: url,
          method: 'POST',
          data: {
            id: id,
            uid: userId 
          }
        }).then((res)=>{
          if(res.flag && res.flag!="3"){
            app.globalData.myUpdate=true;
            handleData();
          }
        })
      } else {
        this.setData({
          isLogin: true
        })
      }
    },
    collectClick(e) {
      this.collectEvent({
        e: e,
        url: 'user/addcollect',
        handleData: ()=>{
          this.setData({collected: true,collect_count: this.data.collect_count+1},()=>wx.showToast({title:'已添加至收藏'}))
        }
      })
    },
    cancelEvent(e){      
      this.collectEvent({
        e: e,
        url: 'user/cancelcollect',
        handleData: ()=>{
          this.setData({collected: false,collect_count: this.data.collect_count-1},()=>wx.showToast({title:'已移除收藏'}))
        }
      })
    },
    actorClick(event) {
      var title = event.currentTarget.dataset.name;
      wx.showToast({
        title,
        icon: 'none'
      })
    },
    longinEvent(){
      this.setData({
        isLogin: true
      })
    },
    onCancel(){
      this.setData({isLogin: false})
    },
    summaryClick() {
      var num = this.data._num;
      if (num == 1) {
        this.setData({
          _num: 2
        })
      } else {
        this.setData({
          _num: 1
        })
      }
    }
  }
})
