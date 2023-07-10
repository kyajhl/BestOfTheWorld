/**
 *   @author KeFan
 *   @date 2023/6/3
 *   @time 17:38
 */

import request from '@/utils/request'

export default {
  getProjectList(searchForm) {
    return request({
      url: '/project/getProjectList',
      method: 'get',
      params: searchForm
    })
  },
  getProject(projectId) {
    return request({
      url: `/project/getProjectById?projectId=${projectId}`,
      method: 'get'
    })
  },
  updateProject(data) {
    return request({
      url: '/project/updateProject',
      method: 'put',
      data
    })
  },
  addProject(data) {
    return request({
      url: '/project/addProject',
      method: 'post',
      data
    })
  },
  saveProject(data) {
    // id 为 666，代表添加，777 代表更新
    if (data.id === '666') {
      return this.addProject(data);
    } else {
      return this.updateProject(data);
    }
  },
  deleteProject(projectId) {
    return request({
      url: '/project/deleteProject',
      method: 'delete',
      params: {projectId}
    })
  }
}
