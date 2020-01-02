let userDao = {
  setUser(user){
    wx.setStorageSync('user', user)
  },
  getUser(){
    if (wx.getStorageSync('user') != '')
      return wx.getStorageSync('user')
    return '';
  },
  getToken() {
    if (wx.getStorageSync('user')!='')
      return wx.getStorageSync('user').authToken
    return '';
  },
  //let security = {account:this.data.account,password:this.data.password}
  setSecurity(security){
    wx.setStorageSync('security', security);
  },
  getAccount(){
    if (wx.getStorageSync('security')!='')
      return wx.getStorageSync('security').account||''
    return null;
  },
  getPassword(){
    if (wx.getStorageSync('security') != '')
      return wx.getStorageSync('security').password||''
    return null;
  }
}

module.exports = userDao