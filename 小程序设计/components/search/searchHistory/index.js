// components/search/searchHistory/index.js
import {
  renderColor
} from '../../../utils/util.js'
var count = 0;
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    value: Array
  },

  /**
   * 组件的初始数据
   */
  data: {
    clearImg: '../../../images/search/clear.png',
    refreshImg: '../../../images/search/refresh.png',
    popular: [],
    history: [],
    data: [{
      title: '快把我哥弄走'
    }, {
      title: '无名之辈'
    }, {
      title: '龙猫'
    }, {
      title: '悲伤逆流成河'
    }, {
      title: '海王'
    }, {
      title: '阿凡达'
    }, {
      title: '喜剧之王'
    }, {
      title: '哈利波特'
    }, {
      title: '泰坦尼克号'
    }, {
      title: '大话西游之大圣娶亲'
    }, {
      title: '加勒比海盗'
    }, {
      title: '神偷奶爸'
    }, {
      title: '超能陆战队'
    }, {
      title: '英雄本色'
    }, {
      title: '你的名字'
    }, {
      title: '无敌破坏王'
    }, {
      title: '复仇者联盟'
    }, {
      title: '钢铁侠'
    }, {
      title: '谍影重重'
    }, {
      title: '生化危机'
    }, {
      title: '同桌的你'
    }, {
      title: '名侦探柯南'
    }, {
      title: '疯狂动物城'
    }, {
      title: '死侍'
    }, {
      title: '蜘蛛侠'
    }, {
      title: '我的少女时代'
    }, {
      title: '赌神'
    }, {
      title: '魔兽'
    }, {
      title: '变形金刚'
    }, {
      title: '毒液'
    }, {
      title: '摩天营救'
    }, {
      title: '无双'
    }, {
      title: '羞羞的铁拳'
    }, {
      title: '我不是药神'
    }, {
      title: '西虹市首富'
    }, {
      title: '唐人街探案2'
    }]
  },

  /**
   * 在组件实例进入页面节点树时执行
   */
  attached() {
    var history = wx.getStorageSync('search-history');
    var arr = [];
    // data以后可能会使用后台接口调用后存入data库
    var data = this.data.data;
    data.forEach((ele, index) => {
      var temp = {
        title: ele.title,
        background: renderColor(index)
      }
      arr.push(temp);
    })
    this.setData({
      popular: arr.slice(0, 6),
      history: (history) ? history : []
    })
  },

  /**
   * 组件的方法列表
   */

  methods: {
    // 刷新‘大家都在搜’内容
    onRefresh: function() {
      count += 6;
      if (count == 36) {
        count = 0;
      }
      var data = this.data.data;
      var arr = [];
      data.forEach((ele, index) => {
        var temp = {
          title: ele.title,
          background: renderColor(index)
        }
        arr.push(temp);
      })
      this.setData({
        popular: arr.slice(count, count + 6)
      })
    },
    // 清除所有的历史搜素
    onClearAll: function() {
      // wx.removeStorageSync('search-history')
      var _this = this;
      wx.removeStorage({
        key: 'search-history',
        success(res) {
          _this.triggerEvent('cleared');
          _this.setData({
            history: [],
            value: []
          })
        }
      })
      
    },
    // 点击搜索记录标签将标签内容传递给父元素
    searchTitleClick: function(e) {
      // 获取当前被点击的标签内容(尚未弄明白，为何要这样使用)
      var value = e._relatedInfo.anchorTargetText;
      this.triggerEvent('title', value);
    }
  }
})