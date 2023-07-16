import Layout from "@/layout/index";

/**
 *   @author KeFan
 *   @date 2023/7/16
 *   @time 15:07
 */
// 企业路由
const enterpriseRoutes = [
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
        children: [
          {
            path: 'team_information',
            name: 'team_information',
            component: () => import('@/views/enterprise/team_information/TeamInformation'),
            meta: {title: '团队信息'},
            hidden: true,
            // 获取路由参数
            props({query: {teamId}}) {
              return {teamId};
            }
          }
        ]
      }
    ]
  }
];

export default enterpriseRoutes;
