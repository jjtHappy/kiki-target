// pages/tap-supervision/reward-detail/reward-detail.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        reward: {},
        viewHeight: 0,
        viewWidth: 0,
        requestBuilder: {},
        userDao: {},
        router: {}
    },

    //图片预览
    handleTapPreviewImage(){
        wx.previewImage({
            urls: [this.data.reward.storePath] // 需要预览的图片http链接列表
        })
    },
    //重置数据
    resetData() {
        this.setData({
            reward: {}
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
        if (options.reward) {
            this.setData({
                reward: JSON.parse(decodeURIComponent(options.reward))
            })
        }
    }
})