let config = require('../../config/config.js')
let userDao = require('../../store/user-dao.js')
let router = require('../../router/router.js')

module.exports=function(baseRequestObj,success,fail,complete){
  baseRequestObj = JSON.parse(JSON.stringify(baseRequestObj))
  baseRequestObj.header.KIKI_AUTH_TOKEN = userDao.getToken()
  baseRequestObj.url = config.BASE_SERVICE_PATH + baseRequestObj.url


  let baseSuccess = (res)=>{
    if (res.statusCode!=200){
      wx.showToast({
        title: '请求失败了:' + res.statusCode,
        icon: 'none',
        duration: 2000
      })
    }else{
      //res.data!=undefined 下载接口是没有data的
      if (res.data!=undefined && res.data.code == 401){//尚未登录
        router.toLogin()
      }else{
        if (typeof success === "function") {
          success(res)
        } 
      }
    }
  }

  baseRequestObj.success = baseSuccess
 


  if (typeof fail === "function"){
    baseRequestObj.fail = fail
  }else{
    let defaultfail = (err)=>{
      console.log(err)
      wx.showToast({
        title: '服务器挂了:' + err.errMsg,
        icon:'none',
        duration:2000
      })
    }
    baseRequestObj.fail = defaultfail
  }


  if (typeof complete === "function")
    baseRequestObj.complete = complete
  return baseRequestObj
}