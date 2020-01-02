module.exports={
    isReload(){
        return getApp().globalData.isSupervisionRewardListReload;
    },
    setReload(){
        getApp().globalData.isSupervisionRewardListReload=true;
    },
    resetReload(){
        getApp().globalData.isSupervisionRewardListReload = false;
    },
    isPartRefresh(){
        return getApp().globalData.supervisionRewardListPartRefresh.length>0
    },
    setPartRefresh(target){
        getApp().globalData.supervisionRewardListPartRefresh.push(target)
    },
    resetPartRefresh(){
        let array = getApp().globalData.supervisionRewardListPartRefresh
        getApp().globalData.supervisionRewardListPartRefresh=[]
        return array
    }
}