const loginPath = '/pages/login/login'
const tapTargetTargetList = '/pages/tap-target/target-list/target-list'
const tapShopRewardList = '/pages/tap-shop/reward-list/reward-list'
const tapTargetTargetDetail = '/pages/tap-target/target-detail/target-detail'
const tapSupervisionTargetDetail = '/pages/tap-supervision/target-detail/target-detail'
const tapTargetTargetComplete = '/pages/tap-target/target-complete/target-complete'
const tapSupervisionReviewedList = '/pages/tap-supervision/reviewed-list/reviewed-list'
const tapSupervisionTargetList = '/pages/tap-supervision/target-list/target-list'
const tapSupervisionRewardList = '/pages/tap-supervision/reward-list/reward-list'
const tapSupervisionRewardAdd = '/pages/tap-supervision/reward-add/reward-add'
const persionPath = '/pages/tap-persion/persion/persion'
const tapPersionReviewedList = '/pages/tap-persion/reviewed-list/reviewed-list'
const toTapPersionExchangeList = '/pages/tap-persion/exchange-list/exchange-list'
const tapSupervisionExchangeList = '/pages/tap-supervision/exchange-list/exchange-list'
const rewardPath = '/pages/reward/reward'
const tapShopExchangeDetail = '/pages/tap-shop/exchange-detail/exchange-detail'
const tapPersionExchangeDetail = '/pages/tap-shop/exchange-detail/exchange-detail'
let router = {
    toLogin() {
        wx.reLaunch({
            url: loginPath,
        })
    },
    toTapTargetTargetList() {
        wx.switchTab({
            url: tapTargetTargetList
        })
    },
    toTapShopRewardList() {
        wx.switchTab({
            url: tapShopRewardList
        })
    },
    toTapTargetTargetComplete(params) {
        console.log(params)
        let url = tapTargetTargetComplete;
        if (params instanceof Array) {
            if (params.length > 0) {
                url = url + '?'
                let key = null;
                let value = null;
                for ({key, value} of params) {
                    url = url + key + '=' + value + '&'
                }
                url.substring(0, url.length - 1)
            }
        }
        wx.navigateTo({
            url: url,
        })
    },
    toTapSupervisionReviewedList() {
        wx.navigateTo({
            url: tapSupervisionReviewedList,
        })
    },
    toTapShopExchangeDetail(params) {
        let url = tapShopExchangeDetail;
        if (params instanceof Array) {
            if (params.length > 0) {
                url = url + '?'
                let key = null;
                let value = null;
                for ({key, value} of params) {
                    url = url + key + '=' + value + '&'
                }
                url.substring(0, url.length - 1)
            }
        }
        wx.navigateTo({
            url: url,
        })
    },
    toTapPersionExchangeDetail(params) {
        let url = tapPersionExchangeDetail;
        if (params instanceof Array) {
            if (params.length > 0) {
                url = url + '?'
                let key = null;
                let value = null;
                for ({key, value} of params) {
                    url = url + key + '=' + value + '&'
                }
                url.substring(0, url.length - 1)
            }
        }
        wx.navigateTo({
            url: url,
        })
    },
    toTapSupervisionTargetList() {
        wx.navigateTo({
            url: tapSupervisionTargetList,
        })
    },
    toTapSupervisionRewardAdd() {
        wx.navigateTo({
            url: tapSupervisionRewardAdd,
        })
    },
    toTapTargetTargetDetail(params) {
        console.log(params)
        let url = tapTargetTargetDetail;
        if (params instanceof Array) {
            if (params.length > 0) {
                url = url + '?'
                let key = null;
                let value = null;
                for ({key, value} of params) {
                    url = url + key + '=' + value + '&'
                }
                url.substring(0, url.length - 1)
            }
        }
        wx.navigateTo({
            url: url,
        })
    },
    toTapSupervisionTargetDetail(params) {
        console.log(params)
        let url = tapSupervisionTargetDetail;
        if (params instanceof Array) {
            if (params.length > 0) {
                url = url + '?'
                let key = null;
                let value = null;
                for ({key, value} of params) {
                    url = url + key + '=' + value + '&'
                }
                url.substring(0, url.length - 1)
            }
        }
        wx.navigateTo({
            url: url,
        })
    },
    toTapPersionReviewedList() {
        wx.navigateTo({
            url: tapPersionReviewedList,
        })
    },
    toTapPersionExchangeList() {
        wx.navigateTo({
            url: toTapPersionExchangeList,
        })
    },
    toTapSupervisionExchangeList() {
        wx.navigateTo({
            url: tapSupervisionExchangeList,
        })
    },
    toTapSupervisionRewardList() {
        wx.navigateTo({
            url: tapSupervisionRewardList,
        })
    },
}

module.exports = router