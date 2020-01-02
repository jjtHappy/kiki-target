let prizeFindallObj = require('../../../request/prize/findall')
let storeDownloadObj = require('../../../request/store/download-file')
let supervisionRewardListInteractive = require('../../../interactive/supervisionRewardListInteractive')
let storeDao = require('../../../store/store-dao')
// pages/tap-supervision/reward-list/reward-list.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        page: {//页面情况
            number: 0,//当前页面
            totalPages: 0,//总页面数
            totalElements: 0,//元素总数
            size: 8
        },
        rewards: [],
        hasMore: true,
        requestBuilder: {},
        userDao: {},
        router: {},
        viewHeight: 0,//屏幕高度
        viewWidth: 0,//屏幕宽度
        imagePath: "/static/images/icon-add-fill.png",
        downloadCompleted: false
    },






    //为某个奖品添加跳转到详情的参数
    addNavigateParamsForReward(reward){
        let jsonStr = JSON.stringify(reward)
        reward.navigateParams = encodeURIComponent(jsonStr)
    },

    //为某个奖品记录添加图片地址,异步方法
    addPictureForReward(reward){
        let storeId = reward.storeId
        let path = storeDao.getStore(storeId)//获取本地路径
        if (path == undefined) {//本地并没有保存
            storeDao.downloadPicture(storeId)
            //第一次用网图并下载
            reward.storePath = this.data.requestBuilder(storeDownloadObj).url+'?storeId='+storeId
        }else{
            reward.storePath = path
        }
    },


    resetPage() {//重置页面情况
        this.setData({
            page: {//页面情况
                number: 0,//当前页面
                totalPages: 0,//总页面数
                totalElements: 0,//元素总数
                size: 8
            }
        })
        console.log('页面情况重置成功')
    },


    //获取页面数据
    loadTable() {
        let requireData = {
            supervisionId: this.data.userDao.getUser().supervisionsIdForSuperintendentId,
            enabled: true,
            page: this.data.page.number,
            size: this.data.page.size
        }
        prizeFindallObj.data = requireData
        let that = this
        wx.request(that.data.requestBuilder(prizeFindallObj, (res) => {
            if (res.data.status) {
                console.log('获取数据成功')
                for (let reward of res.data.data) {
                    that.addPictureForReward(reward)
                    that.addNavigateParamsForReward(reward)
                }
                if (res.data.page.number == 0) {//重头开始
                    that.setData({
                        rewards: res.data.data
                    })
                } else {//从中间开始
                    let rewards = that.data.rewards
                    for (let reward of res.data.data) {
                        rewards.push(reward)
                    }
                    that.setData({
                        rewards: rewards
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
                console.log(that.data.rewards)
            } else {//失败了
                wx.showToast({
                    title: res.data.message,
                    icon: 'none'
                })
                return
            }
        }))
    },


    //跳转到添加奖品
    handleAddReward(){
        this.data.router.toTapSupervisionRewardAdd();
    },

    handleTouchStart(){
        this.setData({
            imagePath: "/static/images/icon-add.png"
        })
    },
    handleTouchEnd(){
        this.setData({
            imagePath: "/static/images/icon-add-fill.png"
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
        //获取数据
        this.resetPage()
        this.loadTable()
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


    onShow(){
        if (supervisionRewardListInteractive.isReload()) {//判断是否整个页面刷新
            //获取数据
            wx.pageScrollTo({
                scrollTop: 0,
                duration: 0
            })
            this.resetPage()
            this.loadTable()
            supervisionRewardListInteractive.resetReload()
        } else {//判断是否局部刷新
            if (supervisionRewardListInteractive.isPartRefresh()) {
                let newRewards = supervisionRewardListInteractive.resetPartRefresh()
                for (let reward of newRewards) {
                    this.addPictureForReward(reward)
                    this.addNavigateParamsForReward(reward)
                    for (let i = 0; i < this.data.rewards.length; i++) {
                        if (this.data.rewards[i].id == reward.id) {
                            this.setData({
                                ['rewards[' + i + ']']: reward
                            })
                        }
                    }
                }
            }
        }
    }
})