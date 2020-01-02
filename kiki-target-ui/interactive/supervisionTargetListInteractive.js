module.exports={
    isReload(){
        return getApp().globalData.isSupervisionTargetListReload;
    },
    setReload(){
        getApp().globalData.isSupervisionTargetListReload=true;
    },
    resetReload(){
        getApp().globalData.isSupervisionTargetListReload = false;
    },
    isPartRefresh(){
        return getApp().globalData.supervisionTargetListPartRefresh.length>0
    },
    setPartRefresh(target){
        getApp().globalData.supervisionTargetListPartRefresh.push(target)
    },
    resetPartRefresh(){
        let array = getApp().globalData.supervisionTargetListPartRefresh
        getApp().globalData.supervisionTargetListPartRefresh=[]
        return array
    }
}