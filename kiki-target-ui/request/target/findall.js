let requestObj = {//请求实体
  url: '/target/findall',//请求地址
  data: "",//请求的参数
  header: {
    'content-type': 'application/json' // 默认值
  },//请求头
  method: "GET"//请求方法
}
module.exports = requestObj