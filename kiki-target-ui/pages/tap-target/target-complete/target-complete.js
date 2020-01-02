let targetCompleteObj = require('../../../request/target/complete.js')
let targetListInteractive = require('../../../interactive/targetTargetListInteractive.js')
// pages/complete-target/complete-target.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    comment:null,
    commentLength:0,
    target:{},
    superintendent: '',
    commitReviewDisabled:false,
    requestBuilder: {},
    userDao: {},
    router: {},
    viewHeight: 0,//屏幕高度
    viewWidth: 0//屏幕宽度
  },
  //重置数据
  resetData(){
    this.setData({
      comment: null,
      commentLength: 0,
      target: {},
      commitReviewDisabled:false
    })
  },
  handleInputComment(event){
    this.setData({
      comment:event.detail.value,
      commentLength:event.detail.value.length
    })
  },
  handleTapCommit(){
    this.setData({
      commitReviewDisabled:true
    })
    let that = this
    if(this.validate()){
      wx.showModal({
        title: '是否提交',
        content: '是否提交给' + that.data.userDao.getUser().superintendent,
        success: function (res) {
          if (res.confirm) {
              wx.showLoading({
                title: '提交中'
              })
              let data = {
                targetId: that.data.target.id,
                comment:that.data.comment
              }
              targetCompleteObj.data = data
              wx.request(that.data.requestBuilder(targetCompleteObj, (res) => {
                wx.hideLoading()
                if (res.data.status) {
                  console.log('提交成功')
                  //让任务列表刷新
                  targetListInteractive.setPartRefresh(res.data.data)
                  that.data.router.toTapTargetTargetList()
                } else {//失败了
                  wx.showToast({
                    title: res.data.message,
                    icon: 'none'
                  })
                  return
                }
              }))
          } else if (res.cancel) {
            return
          }
        }
      })
    }
    this.setData({
      commitReviewDisabled: false
    })
  },
  //表格校验
  validate(){
    if (this.data.commentLength == 0) {
      wx.showToast({
        title: '感想内容不能为空',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
    return true
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.resetData()
    //将参数格式化
    if(options.target){
      this.setData({
        target: JSON.parse(options.target)
      })
    }
    let app = getApp()
    // 获取系统信息
    this.setData({
      viewHeight: app.globalData.viewHeight,
      viewWidth: app.globalData.viewWidth,
      requestBuilder: app.globalData.requestBuilder,
      userDao: app.globalData.userDao,
      router: app.globalData.router,
      superintendent: app.globalData.userDao.getUser().superintendent
    })
   
  }
})