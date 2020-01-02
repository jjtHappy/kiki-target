let userInfoObj = require('../../../request/user/info')
Page({
    data: {
        username: "",
        userAccount: "",
        requestBuilder: {},
        imageSrc:"",
        userDao: {},
        router: {},
        viewHeight: 0,//屏幕高度
        viewWidth: 0//屏幕宽度
    },
    //跳转到我的审核列表
    handleTapReviewed(){
        console.log('跳转到我的审核列表')
        this.data.router.toTapPersionReviewedList()
    },
    //跳转到我的兑换列表
    handleTapExchange(){
        console.log('跳转到我的审核列表')
        this.data.router.toTapPersionExchangeList()
    },
    handleTapLogout(){
        console.log('退出登录')
        //清除除帐号之外的所有信息
        let account = this.data.userDao.getAccount();
        wx.removeStorageSync('user')
        wx.removeStorageSync('security')
        let security = {account: account, password: ''};
        this.data.userDao.setSecurity(security);
        this.data.router.toLogin();
    },
    loadUserInfo(){//从缓存中加载用户数据
        let currentUser = this.data.userDao.getUser();
        this.setData({
            username: currentUser.name,
            userAccount: currentUser.amount
        })
    },
    refreshUserInfo(){
        let that = this
        wx.request(this.data.requestBuilder(userInfoObj,(res)=>{
            if(res.data.status){
                console.log('刷新用户数据成功')
                //存储用户信息
                that.data.userDao.setUser(res.data.data)
            }else{//失败了
                wx.showToast({
                    title: res.data.message,
                    icon:'none'
                })
                return
            }
        }))
    },
    onLoad(){
        let app = getApp()
        // 获取系统信息
        this.setData({
            viewHeight: app.globalData.viewHeight,
            viewWidth: app.globalData.viewWidth,
            requestBuilder: app.globalData.requestBuilder,
            userDao: app.globalData.userDao,
            router: app.globalData.router,
            imageSrc: app.globalData.userInfo == null ? '/static/images/icon-person.png' : app.globalData.userInfo.avatarUrl
        })
    },
    onShow(){
      this.loadUserInfo()
    },
    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {
        this.refreshUserInfo()
        this.loadUserInfo()
        wx.stopPullDownRefresh()
    }
})