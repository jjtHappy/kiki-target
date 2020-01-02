let util = require('../../../utils/util.js');
let numberUtil = require('../../../utils/numberUtil.js')
let fileUploadObj = require('../../../request/store/upload-file')
let prizeAddObj = require('../../../request/prize/add')
let supervisionRewardListInteractive = require('../../../interactive/supervisionRewardListInteractive')
// pages/tap-supervision/reward-add/reward-add.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        requestBuilder: {},
        userDao: {},
        router: {},
        viewHeight: 0,//屏幕高度
        viewWidth: 0,//屏幕宽度
        imagePath: "",

        uploadRate: 0,

        storeId: '',
        name: "",
        nameLength: 0,
        worth: 0,
        score: 0,
        addDisabled: false,
        currentUploadTask: {},
        showRate:false
    },

    //数据重置
    resetData(){
        console.log('添加奖品数据重置')
        this.setData({
            imagePath: "",
            name: "",
            nameLength: 0,
            worth: 0,
            score: 0,
            storeId: '',
            addDisabled: false,
            uploadRate: 0
        })
    },

    //输入名字
    handleInputName(event){
        this.setData({
            name: event.detail.value,
            nameLength: event.detail.value.length
        })
    },

    //输入价格
    handleInputWorth(event){
        let value = event.detail.value
        if (!numberUtil.isRealNum(value) || numberUtil.isFloat(value)) {
            value = ""
        }
        this.setData({
            worth: value
        })
    },

    //输入分数
    handleInputScore(event){
        let value = event.detail.value
        if (!numberUtil.isRealNum(value) || numberUtil.isFloat(value)) {
            value = ""
        }
        this.setData({
            score: parseInt(value)
        })
    },

    //选择图片
    handleTapChooseImage(){
        let that = this;
        //选择图片
        wx.chooseImage({
            count: 1,
            success: function (res) {
                let file = res.tempFiles[0]
                if(file.size >=(1024*1024)){
                    wx.showToast({
                        title: '文件大小不能超过1M',
                        icon: 'none'
                    })
                    return ;
                }
                that.setData({
                    showRate:true
                })
                // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
                that.setData({
                    imagePath: res.tempFilePaths[0]
                });

                //开始图片上传
                that.setData({
                    uploadRate: 0,
                    storeId: ''
                })
                //取消上次的上传任务
                if (typeof that.data.currentUploadTask.abort == 'function') {
                    that.data.currentUploadTask.abort()
                }
                fileUploadObj.filePath = res.tempFilePaths[0]
                that.data.currentUploadTask = wx.uploadFile(that.data.requestBuilder(fileUploadObj, res => {
                    let data = JSON.parse(res.data)
                    if (data.status) {
                        console.log('上传成功')
                        that.setData({
                            showRate:false
                        })
                        wx.showToast({
                            title: '上传成功',
                            icon: 'none'
                        })
                        that.setData({
                            storeId: data.data.storeId
                        })
                    } else {//失败了
                        wx.showToast({
                            title: data.data.message,
                            icon: 'none'
                        })
                        that.setData({
                            uploadRate: 0,
                            storeId: '',
                            imagePath:''
                        })
                        return
                    }
                }))
                that.data.currentUploadTask.onProgressUpdate((res) => {
                    console.log('上传进度', res.progress)
                    that.setData({
                        uploadRate: res.progress
                    })
                })
            }
        })


    },

    //点击确认按钮
    handleTapAdd(){
        this.setData({
            addDisabled: true
        })
        let that = this
        wx.showModal({
            title: '确认添加奖品',
            content: '是否确认添加奖品，确认后奖品不可修改',
            success: function (res) {
                if (res.confirm) {
                    if (that.validate()) {//校验成功
                        wx.showLoading({
                            title: '添加中'
                        })
                        let data = {
                            name: that.data.name,
                            storeId: that.data.storeId,
                            worth: that.data.worth,
                            score: that.data.score
                        }
                        prizeAddObj.data = data
                        wx.request(that.data.requestBuilder(prizeAddObj, (res) => {
                            wx.hideLoading()
                            if (res.data.status) {
                                console.log('添加成功')
                                supervisionRewardListInteractive.setReload()
                                wx.navigateBack()
                            } else {//失败了
                                wx.showToast({
                                    title: res.data.message,
                                    icon: 'none'
                                })
                                return
                            }
                        }))

                    }
                } else if (res.cancel) {
                    return
                }
            }
        })
        this.setData({
            addDisabled: false
        })
    },
    //数据校验
    validate(){
        if (this.data.nameLength == 0) {
            wx.showToast({
                title: '奖品名称不能为空',
                icon: 'none',
                duration: 2000
            })
            return false;
        }
        if (this.data.storeId.length==0) {
            wx.showToast({
                title: '奖品图片不能为空',
                icon: 'none',
                duration: 2000
            })
            return false;
        }
        if (!numberUtil.isRealNum(this.data.worth) || !numberUtil.isRealNum(this.data.score)) {
            wx.showToast({
                title: '请输入价格与分数',
                icon: 'none',
                duration: 2000
            })
            return false;
        }
        if (this.data.worth <= 0 || this.data.score <= 0) {
            wx.showToast({
                title: '价格与分数只能是大于0而小于999',
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
})