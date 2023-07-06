import router from './router'
import store from './store'
import {Message} from 'element-ui'
import nProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import {getToken} from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

import Layout from '@/layout/index'

nProgress.configure({showSpinner: false}); // nProgress Configuration

const whiteList = ['/login']; // no redirect whitelist

// 每次路由跳转前判断有没有 token
router.beforeEach(async (to, from, next) => {
  // 开始进度条
  nProgress.start();

  // 设置页面的标题
  document.title = getPageTitle(to.meta.title);

  // 首先判断登录的用户有没有在 Cookie 中存入 token
  const hasToken = getToken();

  // 如果 cookie 里面有 token
  if (hasToken) {
    if (to.path === '/login') {
      // 如果要跳转到 login 页面，由于有 token ，不需要登录了，直接跳转到首页

      console.log('用户已登录，不需要跳转到登录页面');

      next({path: '/'});
      nProgress.done()
    } else {
      // 如果能够获取用户名(代表没刷新)，要判断是否跳转首页
      const hasGetUserInfo = store.getters.name;
      if (hasGetUserInfo) {
        // 如果要跳转到首页，那么要获取用户信息
        if (to.path === '/dashboard') {
          console.log('没刷新，跳转首页，要获取用户信息');
          try {
            await store.dispatch('user/getRoleId');
            await store.dispatch('user/getInfo');
            next();
          } catch (error) {
            // 获取用户信息失败，就删除 token ，并且重新跳转到 login 页面，重新登录
            await store.dispatch('user/resetToken');
            // Message.error(error || 'Has Error');
            next(`/login?redirect=${to.path}`);
            // next('/login');
            nProgress.done();
          }
        } else {
          console.log('没刷新，没跳转首页，直接放行');
          next()
        }
      } else {
        // 这里获取的用户名为 空，代表 store 里面已经重置了，即刷新了页面，那么就要重新获取用户信息
        // 也就是说每次只要刷新页面，就会重新获取用户信息，可以测试 token 是否有效
        // 第一次登陆进去，由于用户名为空，所以也要获取用户信息
        try {
          // 调用 store 里面 user 模块里的 getInfo方法
          await store.dispatch('user/getRoleId');
          await store.dispatch('user/getInfo');

          // 路由转换
          // let myRoutes = myFilterAsyncRoutes(store.getters.menuList);
          // // 404
          // myRoutes.push({
          //   path: '*',
          //   redirect: '/404',
          //   hidden: true
          // });
          // // 动态添加路由
          // router.addRoutes(myRoutes);
          // // 存至全局变量
          // global.myRoutes = myRoutes;
          // next({...to, replace: true});  // 防止刷新后页面空白


          next()
        } catch (error) {
          // 获取用户信息失败，就删除 token ，并且重新跳转到 login 页面，重新登录
          await store.dispatch('user/resetToken');
          // Message.error(error || 'Has Error');
          next(`/login?redirect=${to.path}`);
          // next('/login');
          nProgress.done();
        }
      }
    }
  }
  else {
    if (to.path === '/register') {
      next();
      return;
    }
    // 如果没有 token ，那么直接跳转到 login 页面登录
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`);
      // next('/login');
      nProgress.done()
    }
  }
});

// 进度条结束
router.afterEach(() => {
  // finish progress bar
  nProgress.done()
});

// 对路由进行过滤，相当于上权限
function myFilterAsyncRoutes(menuList) {
  menuList.filter(menu => {
    if (menu.component === 'Layout') {
      menu.component = Layout;

    } else {
      menu.component = require(`@/views/${menu.component}`).default
    }
    // 递归处理子菜单
    if (menu.children && menu.children.length) {
      menu.children = myFilterAsyncRoutes(menu.children)
    }
    return true;
  });
  return menuList;
}
