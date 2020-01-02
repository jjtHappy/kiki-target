let requestObj = {//请求实体
  url: '/exchange/add',//请求地址
  data: null,//请求的参数
  header: {
    'content-type': 'application/json' // 默认值
  },//请求头
  method: "POST"//请求方法
}
module.exports = requestObj