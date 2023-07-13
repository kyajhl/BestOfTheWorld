/**
 *   @author KeFan
 *   @date 2023/6/3
 *   @time 17:38
 */

import request from '@/utils/request'
import da from "element-ui/src/locale/lang/da";

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
  },
  getStudentName(mobilephone) {
    return request({
      url: '/student/getStudentNameByMobilePhone',
      method: 'get',
      params: {mobilephone}
    })
  },
  getTeamList(searchForm) {
    return request({
      url: '/team/getTeamList',
      method: 'get',
      params: searchForm
    })
  },
  getTeam(teamId) {
    return request({
      url: `/student-team/getTeamInfById?teamId=${teamId}`,
      method: 'get'
    })
  },
  updateTeam(data) {
    return request({
      url: `/student-team/updateStudentTeam?teamName=${data.teamName}&teamId=${data.teamId}&projectId=${data.projectId}&selectedStudentList=${data.selectedStudentList}`,
      method: 'put'
      // params: {
      //   teamName: data.teamName,
      //   teamId: data.teamId,
      //   projectId: data.projectId,
      //   selectedStudentList: data.selectedStudentList
      // }
    })
  },
  addTeam(data) {
    return request({
      url: '/team/addTeam',
      method: 'post',
      data
    })
  },
  saveTeam(data) {
    // id 为 666，代表添加，777 代表更新
    if (data.id === '666') {
      console.log("添加");
      return this.addTeam(data);
    } else {
      console.log("更新");
      return this.updateTeam(data);
    }
  },
  deleteTeam(teamId) {
    return request({
      url: '/student-team/deleteTeam',
      method: 'delete',
      params: {teamId}
    })
  }
}
