// pages/detail-target/detail-target.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    target: {},
    viewHeight: 0,
    viewWidth: 0,
    requestBuilder: {},
    userDao: {},
    router: {}
  },
  //重置数据
  resetData() {
    this.setData({
      target: {}
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let app = getApp()
    //定义高度与宽度
    this.setData({
      viewHeight: app.globalData.viewHeight,
      viewWidth: app.globalData.viewWidth,
      requestBuilder: app.globalData.requestBuilder,
      userDao: app.globalData.userDao,
      router: app.globalData.router
    })
    this.resetData()
    //将参数格式化
    if (options.target) {
      this.setData({
        target: JSON.parse(options.target)
      })
    }
  }
})