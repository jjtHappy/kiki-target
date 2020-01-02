let reviewedCount = require('../../../request/reviewed/count.js')
let userSupervisedInfo = require('../../../request/user/supervisedInfo.js')
// pages/supervision/supervision.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        superintendent: '',
        superintendentAmount: 0,
        unreatedCount: 0,
        requestBuilder: {},
        userDao: {},
        router: {},
        viewHeight: 0,//屏幕高度
        viewWidth: 0//屏幕宽度
    },

    //跳转到兑换记录
    handleTapExchangeList(){
        this.data.router.toTapSupervisionExchangeList()
    },
    //跳转到审核列表
    handleTapReviewed(){
        this.data.router.toTapSupervisionReviewedList()
    },
    //跳转到他的任务列表
    handleTapTargets(){
      this.data.router.toTapSupervisionTargetList()
    },
    //跳转到奖品列表
    handleTapRewardList(){
        this.data.router.toTapSupervisionRewardList()
    },

    //加载未读审核
    loadReviewedCount(){
        reviewedCount.data = {
            state: 1,
            enabled: true,
            supervisionId: this.data.userDao.getUser().supervisionsIdForSuperintendentId
        }
        let that = this
        wx.request(that.data.requestBuilder(reviewedCount, (res) => {
            if (res.data.status) {
                console.log('获取未处理审核成功')
                that.setData({
                    unreatedCount: res.data.data.count
                })
            } else {//失败了
                wx.showToast({
                    title: res.data.message,
                    icon: 'none'
                })
                return
            }
        }))
    },
    //加载监督者信息
    loadUserSupervisedInfo() {
        let that = this
        wx.request(that.data.requestBuilder(userSupervisedInfo, (res) => {
            if (res.data.status) {
                console.log('获取监督人信息成功')
                console.log(res.data.data)
                that.setData({
                    superintendent: res.data.data.name,
                    superintendentAmount: res.data.data.amount,
                })
            } else {//失败了
                wx.showToast({
                    title: res.data.message,
                    icon: 'none'
                })
                return
            }
        }))
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        let app = getApp()
        //定义高度与宽度
        this.setData({
            viewHeight: app.globalData.viewHeight,
            viewWidth: app.globalData.viewWidth,
            requestBuilder: app.globalData.requestBuilder,
            userDao: app.globalData.userDao,
            router: app.globalData.router
        })
    },
    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        this.loadReviewedCount()
        this.loadUserSupervisedInfo()
    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {
        this.loadReviewedCount()
        this.loadUserSupervisedInfo()
        wx.stopPullDownRefresh()
    }
})