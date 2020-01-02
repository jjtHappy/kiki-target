module.exports={
  isRealNum(val) {
    // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
    if (val === "" || val == null) {
      return false;
    }
    if (!isNaN(val)) {
      return true;
    } else {
      return false;
    }
  },
  isFloat(val){
    var result = (String(val)).indexOf(".");
    return result > -1
  }     
}