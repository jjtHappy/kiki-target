let requestObj = {//请求实体
    url: '/store/upload/picture',//请求地址
    filePath: '',//文件路径
    name:'file',//对应的参数名
    data: null,//请求的参数
    header: {
        'content-type': 'multipart/form-data' // 默认值
    },//请求头
    method: "GET"//请求方法
}
module.exports = requestObj