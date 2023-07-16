import Vue from 'vue'
import Router from 'vue-router'

// 引入 store
import store from "@/store";
// 引入 getters
import getters from "@/store/getters";

Vue.use(Router);

import Layout from '@/layout/index'

export const constantRoutes = [
  // 登录路由
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  // 注册路由
  {
    path: '/register',
    component: () => import('@/views/register/index'),
    hidden: true
  },

  // 404 路由
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  // 根路由，即首页
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: {title: '首页', icon: 'dashboard', affix: true}
    }]
  },

  // 个人信息路由
  {
    path: '/user',
    name: 'user',
    component: Layout,
    redirect: '/user/information',
    hidden: true,
    children: [
      {
        path: 'information',
        name: 'information',
        component: getComponentByRoleId,
        meta: {title: '个人信息'}
      },
    ]
  }
];

// 个人信息中判断角色 id 来导入哪个组件
function getComponentByRoleId() {
  if (getters.roleId(store.state) === '1') return import('@/views/user/student');
  if (getters.roleId(store.state) === '2') return import('@/views/user/teacher');
  if (getters.roleId(store.state) === '3') return import('@/views/user/enterprise');
}

// 创建路由器的一个箭头函数，返回值是一个路由器
const createRouter = () => new Router({
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
});

const router = createRouter();

export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher
}

export default router
