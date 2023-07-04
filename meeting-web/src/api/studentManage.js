/**
 *   @author KeFan
 *   @date 2023/5/31
 *   @time 16:19
 */

import request from '@/utils/request'

export default {
  getAllCourseList(searcherForm) {
    return request({
      url: '/course/getAllCourseList',
      method: 'get',
      params: searcherForm
    })
  },
  addSelectedCourse(data) {
    return request({
      url: '/studentCourse/addSelectedCourse',
      method: 'post',
      data
    })
  },
  deleteSelectedCourse(data) {
    return request({
      url: '/studentCourse/deleteSelectedCourse',
      method: 'delete',
      data
    })
  },
  getSelectedCourseList(searchForm) {
    return request({
      url: '/studentCourse/getSelectedCourseList',
      method: 'get',
      params: searchForm
    })
  },
  getSelectedCourseAndGradeList(searchForm) {
    return request({
      url: '/studentCourse/getSelectedCourseAndGradeList',
      method: 'get',
      params: searchForm
    })
  },
  modifyStudentGrade(data) {
    return request({
      url: '/studentCourse/modifyStudentGrade',
      method: 'put',
      data
    })
  }
}
