let storeDownloadObj = require('../request/store/download-file')
let requestBuilder = require('../request/factory/requestObjBuilder')
let storeDao = {
    setStore(storeId,path){
        let stores = wx.getStorageSync('store')||{};
        stores[storeId]=path
        wx.setStorageSync('store', stores)
    },
    getStore(storeId){
        let stores = wx.getStorageSync('store')||{};
        return stores[storeId];
    },
    //下载图片资源并存储
    downloadPicture(storeId){
        let that = this
        let requetObj = JSON.parse(JSON.stringify(storeDownloadObj))
        requetObj.url = requetObj.url + "?storeId=" + storeId + ""
        wx.downloadFile(requestBuilder(requetObj, (res) => {
            console.log(res);
            console.log('下载资源:' + storeId + ' 成功,' + '临时目录：' + res.tempFilePath)
            //存储到本地
            wx.saveFile({
                tempFilePath: res.tempFilePath,
                success: function (res) {
                    console.log('存储到本地成功')
                    that.setStore(storeId, res.savedFilePath)
                }
            })

        }))
    }
}

module.exports = storeDao