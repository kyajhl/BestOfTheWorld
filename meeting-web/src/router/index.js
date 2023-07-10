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

  // 学生管理路由
  {
    path: '/student',
    component: Layout,
    redirect: '/student/course_selection_system',
    name: 'student',
    meta: {title: '学生管理', icon: 'student_management'},
    // 选课系统，查询课程成绩，查询已选课程
    children: [
      {
        path: 'course_selection_system',
        name: 'course_selection_system',
        component: () => import('@/views/student/CourseSelectionSystem'),
        meta: {title: '选课系统', icon: 'course_selection_system'},
      },
      {
        path: 'course_grade',
        name: 'course_grade',
        component: () => import('@/views/student/CourseGrade'),
        meta: {title: '课程成绩', icon: 'course_grade'}
      },
      {
        path: 'course_selected',
        name: 'course_selected',
        component: () => import('@/views/student/CourseSelected'),
        meta: {title: '已选课程', icon: 'course_selected'}
      }
    ]
  },

  // 教师管理路由
  {
    path: '/teacher',
    component: Layout,
    redirect: '/teacher/teaching_course',
    name: 'teacher',
    meta: {title: '教师管理', icon: 'teacher_management'},
    // 查询教授课程，记录学生成绩
    children: [
      {
        path: 'teaching_course',
        name: 'teaching_course',
        component: () => import('@/views/teacher/TeachingCourse'),
        meta: {title: '教授课程', icon: 'teaching_course'}
      },
      {
        path: 'record_grade',
        name: 'record_grade',
        component: () => import('@/views/teacher/RecordGrade'),
        meta: {title: '记录成绩', icon: 'record_grade'},
        children: [
          {
            path: 'all_student',
            name: 'all_student',
            component: () => import('@/views/teacher/student/AllStudent'),
            meta: {title: '学生列表'},
            hidden: true,
            // 获取路由参数
            props({query: {teacherId, cno}}) {
              return {teacherId, cno};
            }
          }
        ]
      }
    ]
  },

  // 企业管理路由
  {
    path: '/enterprise',
    name: 'enterprise',
    component: Layout,
    redirect: '/enterprise/project_operation',
    meta: {title: '企业管理', icon: 'enterprise_management'},
    // 管理项目创建，团队创建
    children: [
      {
        path: 'project_operation',
        name: 'project_operation',
        component: () => import('@/views/enterprise/ProjectOperation'),
        meta: {title: '项目操作', icon: 'project_operation'},
        children: [
          {
            path: 'project_information',
            name: 'project_information',
            component: () => import('@/views/enterprise/project_information/ProjectInformation'),
            meta: {title: '项目信息'},
            hidden: true,
            // 获取路由参数
            props({query: {projectId}}) {
              return {projectId};
            }
          }
        ]
      },
      {
        path: 'team_operation',
        name: 'team_operation',
        component: () => import('@/views/enterprise/TeamOperation'),
        meta: {title: '团队操作', icon: 'team_operation'},
      }
    ]
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
  },

  // 404 page must be placed at the end !!!
  {
    path: '*',
    redirect: '/404',
    hidden: true
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
