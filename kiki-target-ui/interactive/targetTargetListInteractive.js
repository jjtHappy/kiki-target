module.exports={
  isReload(){
    return getApp().globalData.isTargetTargetListReload;
  },
  setReload(){
    getApp().globalData.isTargetTargetListReload=true;
  },
  resetReload(){
    getApp().globalData.isTargetTargetListReload = false;
  },
  isPartRefresh(){
    return getApp().globalData.targetTargetListPartRefresh.length>0
  },
  setPartRefresh(target){
    getApp().globalData.targetTargetListPartRefresh.push(target)
  },
  resetPartRefresh(){
    let array = getApp().globalData.targetTargetListPartRefresh
    getApp().globalData.targetTargetListPartRefresh=[]
    return array
  }
}