module.exports = {
  /**
* 将日期格式化成指定格式的字符串
* @param date 要格式化的日期，不传时默认当前时间，也可以是一个时间戳
* @param fmt 目标字符串格式，支持的字符有：y,M,d,q,w,H,h,m,S，默认：yyyy-MM-dd HH:mm:ss
* @returns 返回格式化后的日期字符串
*/
  formatDate(date, fmt) {
    date = date == undefined ? new Date() : date;
    date = typeof date == 'number' ? new Date(date) : date;
    fmt = fmt || 'yyyy-MM-dd HH:mm:ss';
    var obj =
      {
        'y': date.getFullYear(), // 年份，注意必须用getFullYear
        'M': date.getMonth() + 1, // 月份，注意是从0-11
        'd': date.getDate(), // 日期
        'q': Math.floor((date.getMonth() + 3) / 3), // 季度
        'w': date.getDay(), // 星期，注意是0-6
        'H': date.getHours(), // 24小时制
        'h': date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, // 12小时制
        'm': date.getMinutes(), // 分钟
        's': date.getSeconds(), // 秒
        'S': date.getMilliseconds() // 毫秒
      };
    var week = ['天', '一', '二', '三', '四', '五', '六'];
    for (var i in obj) {
      fmt = fmt.replace(new RegExp(i + '+', 'g'), function (m) {
        var val = obj[i] + '';
        if (i == 'w') return (m.length > 2 ? '星期' : '周') + week[val];
        for (var j = 0, len = val.length; j < m.length - len; j++) val = '0' + val;
        return m.length == 1 ? val : val.substring(val.length - m.length);
      });
    }
    return fmt;
  },
  parseDate(str, fmt) {
    fmt = fmt || 'yyyy-MM-dd';
    var obj = { y: 0, M: 1, d: 0, H: 0, h: 0, m: 0, s: 0, S: 0 };
    fmt.replace(/([^yMdHmsS]*?)(([yMdHmsS])\3*)([^yMdHmsS]*?)/g, function (m, $1, $2, $3, $4, idx, old){
      str = str.replace(new RegExp($1 + '(\\d{' + $2.length + '})' + $4), function (_m, _$1) {
        obj[$3] = parseInt(_$1);
        return '';
      });
      return '';
    });
    obj.M--; // 月份是从0开始的，所以要减去1
    var date = new Date(obj.y, obj.M, obj.d, obj.H, obj.m, obj.s);
    if (obj.S !== 0) date.setMilliseconds(obj.S); // 如果设置了毫秒
    return date;
  }
}