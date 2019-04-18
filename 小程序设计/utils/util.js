// 处理名字，当名字长度过长时截取
function sliceName(name) {
  if (name.length > 7) {
    return name = name.slice(0, 7)
  }
  return name
}
// 根据评分多少处理星星
function star(average) {
  var value = average / 2;
  var g = parseInt(value);
  var w = (value - g) * 10;
  var arr = [];
  var count = 0;
  for (var i = 0; i < 5; i++) {
    if (i < g) {
      // 1代表一颗星
      arr.push(1)
    } else {
      count += 1;
      if (count == 1) {
        if (w == 0) {
          // 0代表一颗空星
          arr.push(0);
        } else if (w < 5) {
          // 2代表小半颗星
          arr.push(2)
        } else if (w == 5) {
          // 3代表半颗星
          arr.push(3)
        } else {
          // 4代表大半颗星
          arr.push(4)
        }
      } else {
        arr.push(0)
      }
    }
  }
  return arr
}
// 处理导演
function direct(arr) {
  if (arr[0]) {
    var newArr = "";
    arr.forEach((ele, index) => {
      if (index != arr.length - 1) {
        newArr += ele.name + "、"
      } else {
        newArr += ele.name
      }
    });
    return newArr
  } else return "未确定导演"
}
// 处理演员
function actor(arr) {
  if (arr[0]) {
    var newArr = [];
    arr.forEach(ele => {
      if (ele.avatars) {
        var temp = {
          avatar: ele.avatars.large,
          name: ele.name
        }
      } else {
        var temp = {
          name: ele.name
        }
      }
      newArr.push(temp)
    });
    return newArr
  } else return [{name: "未确定演员"}]
}
// 处理评论
function comment(comments) {
  if (comments.length>0) {
    // var newArr = [];
    // comments.forEach(ele => {
    //   var temp = {
    //     avatar: ele.author.avatar,
    //     name: ele.author.name,
    //     content: ele.content
    //   }
    //   newArr.push(temp)
    // });
    // return newArr
    return comments
  } else return "暂未评论"
}
// 渲染颜色，每5个一循环
const renderColor = (index) => {
  var val = index % 5;
  var colors = ['#89C1FF', '#E0AAF2', '#FFD381', '#64D8CB', '#F56C8B'];
  return colors[val]
}

const formatTime = (date,ymd=false) => {
  const hour = date.getHours()
  const minute = date.getMinutes()
  if(ymd) {
    const year = date.getFullYear()
    const month = date.getMonth() + 1
    const day = date.getDate()
    // const second = date.getSeconds()
    return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute/* , second */].map(formatNumber).join(':')
  }
  return /* [year, month, day].map(formatNumber).join('/') + ' ' +  */[hour, minute/* , second */].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

export {
  sliceName,
  star,
  direct,
  actor,
  comment,
  renderColor,
  formatTime
}