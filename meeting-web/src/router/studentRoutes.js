import Layout from "@/layout/index";

/**
 *   @author KeFan
 *   @date 2023/7/16
 *   @time 15:06
 */

// 学生路由
const studentRoutes = [
  // 学生管理路由
  {
    path: '/student',
    component: Layout,
    redirect: '/student/student_operation',
    name: 'student',
    meta: {title: '学生管理', icon: 'student_management'},
    // 学生操作
    children: [
      {
        path: 'student_operation',
        name: 'student_operation',
        component: () => import('@/views/student/StudentOperation'),
        meta: {title: '学生操作', icon: 'student_operation'},
        children: [
          {
            path: 'student_team_information',
            name: 'student_team_information',
            component: () => import('@/views/student/team_information/TeamInformation'),
            meta: {title: '已选团队信息'},
            hidden: true,
            // 获取路由参数
            props({query: {teamId}}) {
              return {teamId};
            }
          }
        ]
      },
      {
        path: 'information_show',
        name: 'information_show',
        component: () => import('@/views/student/InformationShow'),
        meta: {title: '信息交流', icon: 'information_show'},
      }
    ]
  }
];

export default studentRoutes
