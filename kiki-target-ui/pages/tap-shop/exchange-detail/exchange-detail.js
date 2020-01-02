// pages/tap-shop/exchange-detail/exchange-detail.js
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
        router: {},
        exchange:{}
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
        //将参数格式化
        if (options.exchange) {
            console.log(options.exchange)
            this.setData({
                exchange: JSON.parse(decodeURIComponent(options.exchange))
            })
        }
    }
})