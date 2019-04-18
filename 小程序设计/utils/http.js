import { config } from "../config";
class HTTP {
    request({url,method="GET",data={}}) {
        const promise = new Promise((resolve, reject) => {
            wx.request({
                url: config.base_api_url + url,
                header: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                data,
                method,
                success: (res=>{
                    const statusCode = res.statusCode.toString();
                    if (statusCode.startsWith("2")) {
                        resolve(res.data);
                    } else {
                        this._show_error();
                    }

                }),
                fail: (err=> {
                    reject(err);
                    this._show_error();
                })
            })
        })
        return promise;
    }
    _show_error() {
        wx.showToast({
            title: '网络错误',
            icon: 'none'
        })
    }
}
export {
    HTTP
};