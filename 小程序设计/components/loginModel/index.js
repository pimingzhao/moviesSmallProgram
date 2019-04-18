// components/loginModel/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

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
    onDetermine(){
      wx.switchTab({
        url: '/pages/mine/mine',
      })
    }, 
    onCancelEvent(){
      // 可以选择传值或者不传值
      this.triggerEvent('onCancel')
    }
  }
})
