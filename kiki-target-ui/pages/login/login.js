// pages/login/login.js
let userLoginObj = require('../../request/user/login.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    account:'',
    password:'',
    viewHeight: 0,
    viewWidth: 0,
    requestBuilder: {},
    userDao:{},
    router: {}
  },
  //登录控制
  handleTapLogin(){
    if (this.validate()) { //数据校验
      userLoginObj.data = { account: this.data.account,password:this.data.password}
      let that = this 
      wx.request(this.data.requestBuilder(userLoginObj,(res)=>{
        if(res.data.status){
          console.log('登录成功')
          //存储帐号与密码
          let security = { account: that.data.account, password: that.data.password}
          that.data.userDao.setSecurity(security)
          //存储用户信息
          that.data.userDao.setUser(res.data.data)
          console.log('页面跳转')
          that.data.router.toTapTargetTargetList()
          // that.data.router.toTapShopRewardList()
          // that.data.router.toTapShopExchangeDetailList()
          // that.data.router.toTapSupervisionRewardList()
          // that.data.router.toTapSupervisionRewardAdd()
          //that.data.router.toTapSupervisionTargetList()
        }else{//失败了
          wx.showToast({
            title: res.data.message,
            icon:'none'
          })
          return
        }
      }))
    }else{

    }
  },
  //form校验
  validate(){
    if(this.data.account==''){
      wx.showToast({
        title: '账户不能为空',
        icon: 'none'
      })
      return false
    }
     
    if(this.data.password==''){
      wx.showToast({
        title: '密码不能为空',
        icon: 'none'
      })
      return false
    }
     return true
  },
  handleInputAccount(even){
    this.setData({account:even.detail.value})
  },
  handleInputPassword(even){
    this.setData({password:even.detail.value})
  },
  onShow(){
    let account = this.data.userDao.getAccount()
    let password = this.data.userDao.getPassword()
    if (account != null && password != null) {
      this.setData({
        account:account,
        password:password
      })
      this.handleTapLogin()
    } 
  },
  onLoad(){
    let app = getApp()
    //定义高度与宽度
    this.setData({
      viewHeight: app.globalData.viewHeight,
      viewWidth: app.globalData.viewWidth,
      requestBuilder: app.globalData.requestBuilder,
      userDao: app.globalData.userDao,
      router: app.globalData.router
    })
  }
})