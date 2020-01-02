let userInfoObj = require('../../../request/user/info')
let exchangeAddObj = require('../../../request/exchange/add')
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

    handleTapExchange(event){
        console.log('兑换奖品')
        let reward = event.currentTarget.dataset.reward;
        let that = this
        wx.showModal({
            title: '是否兑换',
            content: '是否向' + that.data.userDao.getUser().superintendent+'兑换奖品，兑换成功后你将扣取'+that.data.reward.score+'分',
            success: function (res) {
                if (res.confirm) {
                    wx.showLoading({
                        title: '提交中'
                    })
                    let data = {
                        prizeId: reward.id
                    }
                    exchangeAddObj.data = data
                    wx.request(that.data.requestBuilder(exchangeAddObj, (res) => {
                        wx.hideLoading()
                        if (res.data.status) {
                            let exchange = res.data.data
                            //刷新帐号信息
                            that.refreshUserInfo()
                            that.data.router.toTapShopExchangeDetail([{key:'exchange',value:JSON.stringify(exchange)}])
                            console.log('跳转成功')
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
    },

    //刷新帐号信息
    refreshUserInfo() {
      let that = this
      wx.request(this.data.requestBuilder(userInfoObj, (res) => {
        if (res.data.status) {
          console.log('刷新用户数据成功')
          //存储用户信息
          that.data.userDao.setUser(res.data.data)
        } else {//失败了
          wx.showToast({
            title: res.data.message,
            icon: 'none'
          })
          return
        }
      }))
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