import Layout from "@/layout/index";

/**
 *   @author KeFan
 *   @date 2023/7/16
 *   @time 15:07
 */
// 教师路由
const teacherRoutes = [
  // 教师管理路由
  {
    path: '/teacher',
    component: Layout,
    redirect: '/teacher/guide_student',
    name: 'teacher',
    meta: {title: '教师管理', icon: 'teacher_management'},
    // 指导学生
    children: [
      {
        path: 'guide_student',
        name: 'guide_student',
        component: () => import('@/views/teacher/GuideStudent'),
        meta: {title: '指导学生', icon: 'student_information'},
        children: [
          {
            path: 'student_information',
            name: 'student_information',
            component: () => import('@/views/teacher/student/StudentInformation'),
            meta: {title: '学生信息'},
            hidden: true,
            // 获取路由参数
            props({query: {mobilephone}}) {
              return {mobilephone};
            }
          }
        ]
      }
    ]
  }
];

export default teacherRoutes;
