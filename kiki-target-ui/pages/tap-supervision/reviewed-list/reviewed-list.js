let reviewedFindall = require('../../../request/reviewed/findall.js')
let reviewedVerify = require('../../../request/reviewed/verify.js')
let supervisionTargetListInteractive = require('../../../interactive/supervisionTargetListInteractive')
// pages/reviewed/reviewed.js
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
    //
    handleTapReject(event) {//拒绝操作
        let reviewed = event.currentTarget.dataset.reviewed;
        let that = this
        wx.showModal({
            title: '否决',
            content: '是否否决任务结果，否决后任务将变成进行状态',
            success: function (res) {
                if (res.confirm) {
                    console.log('进行请求')
                    that.verifyReviewed(reviewed.id, false)
                } else {
                    return
                }
            }
        });
    },
    handleTapAgree(event) {//同意操作
        let reviewed = event.currentTarget.dataset.reviewed;
        console.log(reviewed)
        let that = this
        wx.showModal({
            title: '同意',
            content: '同意任务结果，同意后他将奖励' + reviewed.targetReward + '积分',
            success: function (res) {
                if (res.confirm) {
                    that.verifyReviewed(reviewed.id, true)
                } else {
                    return
                }
            }
        })
    },
    //审批分装
    verifyReviewed(reviewedId, reply) {
        let data = {reviewedId, reply}
        reviewedVerify.data = data
        let that = this
        wx.request(that.data.requestBuilder(reviewedVerify, (res) => {
            if (res.data.status) {
                for (let i = 0; i < that.data.revieweds.length; i++) {
                    if (that.data.revieweds[i].id == res.data.data.id) {
                        //如果是最后一个的话直接移除
                        if(i==that.data.revieweds.length-1){
                            that.data.revieweds.pop()
                            that.setData({
                                revieweds: that.data.revieweds
                            })
                            break
                        }
                        for (let x = i; x < that.data.revieweds.length - 1; x++) {
                            that.data.revieweds[x] = that.data.revieweds[x + 1]
                            that.data.revieweds.pop()
                            that.setData({
                                revieweds: that.data.revieweds
                            })
                        }
                        break
                    }
                }
                wx.showToast({
                    title: '操作成功',
                })
                supervisionTargetListInteractive.setReload()
            } else {//失败了
                wx.showToast({
                    title: res.data.message,
                    icon: 'none'
                })
                return
            }
        }))
    },

    //获取页面数据
    loadTable() {
        let requireData = {
            supervisionId: this.data.userDao.getUser().supervisionsIdForSuperintendentId,
            enabled: true,
            page: this.data.page.number,
            size: this.data.page.size,
            state: 1
        }
        reviewedFindall.data = requireData
        console.log(reviewedFindall)
        let that = this
        wx.request(that.data.requestBuilder(reviewedFindall, (res) => {
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
    }
})