let reviewedFindall = require('../../../request/reviewed/findall.js')      
// pages/my-reviewed/my-reviewed.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    page: {//页面情况
      number: 0,//当前页面
      totalPages: 0,//总页面数
      totalElements: 0,//元素总数
      size: 5
    },
    revieweds: [],
    hasMore: true,
    requestBuilder: {},
    userDao: {},
    router: {},
    viewHeight: 0,//屏幕高度
    viewWidth: 0//屏幕宽度
  },

  //获取页面数据
  loadTable() {
    let requireData = {
      supervisionId: this.data.userDao.getUser().supervisionsIdForUserId,
      enabled: true,
      page: this.data.page.number,
      size: this.data.page.size
    }
    reviewedFindall.data = requireData
    let that = this
    wx.request(this.data.requestBuilder(reviewedFindall, (res) => {
      if (res.data.status) {
        console.log('获取数据成功')
        if (res.data.page.number == 0) {//重头开始
          that.setData({
            revieweds: res.data.data
          })
        } else {//从中间开始
          let revieweds = that.data.revieweds
          for (let reviewed of res.data.data) {
            revieweds.push(reviewed)
          }
          that.setData({
            revieweds: revieweds
          })
        }
        that.setData({
          page: {//页面情况
            number: res.data.page.number,//当前页面
            totalPages: res.data.page.totalPages,//总页面数
            totalElements: res.data.page.totalElements,//元素总数
            size: that.data.page.size
          }
        })
        let hasMore = false
        if (that.data.page.totalPages - 1 > that.data.page.number) {
          hasMore = true
        }
        that.setData({
          hasMore: hasMore
        })
        console.log(that.data.page)
        console.log(that.data.revieweds)
      } else {//失败了
        wx.showToast({
          title: res.data.message,
          icon: 'none'
        })
        return
      }
    }))
  },

  resetPage() {//重置页面情况
    this.setData({
      page: {//页面情况
        number: 0,//当前页面
        totalPages: 0,//总页面数
        totalElements: 0,//元素总数
        size: 5
      }
    })
    console.log('页面情况重置成功')
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let app = getApp()
    // 获取系统信息
    this.setData({
      viewHeight: app.globalData.viewHeight,
      viewWidth: app.globalData.viewWidth,
      requestBuilder: app.globalData.requestBuilder,
      userDao: app.globalData.userDao,
      router: app.globalData.router
    })
    //获取数据
    this.resetPage()
    this.loadTable()
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
    console.log('上拉刷新')
    this.resetPage()
    this.loadTable()
    wx.stopPullDownRefresh()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    console.log('下拉触底')
    if (this.data.hasMore) {//还有更多内容
      this.setData({
        page: {//页面情况
          number: this.data.page.number + 1,//当前页面
          totalPages: this.data.page.totalPages,//总页面数
          totalElements: this.data.page.totalElements,//元素总数
          size: this.data.page.size
        }
      })
      //加载数据
      this.loadTable()
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})