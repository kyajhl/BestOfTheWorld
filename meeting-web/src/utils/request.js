import axios from 'axios'
import {MessageBox, Message} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'
//引入进度条
import nProgress from "nprogress";
//引入进度条样式
import 'nprogress/nprogress.css';

// 用 axios 创建一个实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
});

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 进度条开始滚动
    // nProgress.start();

    if (store.getters.token) {
      // 每次请求前在请求头中带 token，这样后端拦截器在检测时会判断用户是否有效
      config.headers['Access-Token'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error); // for debug
    return Promise.reject(error)
  }
);

// 响应拦截器
service.interceptors.response.use(
  response => {
    // 进度条结束滚动
    // nProgress.done();

    const res = response.data;
    if (res.code !== 200) {
      Message({
        // 这里真的 "操了"，一个 msg 和 message 不匹配也是醉了
        message: res.msg || '登录失败或者登录信息失效',
        type: 'error',
        duration: 5 * 1000
      });
      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
        // to re-login
        MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
          confirmButtonText: 'Re-Login',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error); // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    });
    return Promise.reject(error)
  }
);

export default service
