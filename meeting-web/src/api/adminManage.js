/**
 *   @author KeFan
 *   @date 2023/6/3
 *   @time 17:38
 */

import request from '@/utils/request'
import da from "element-ui/src/locale/lang/da";

export default {
  getTeacherList(searchForm) {
    return request({
      url: '/teacher/getTeacherList',
      method: 'get',
      params: searchForm
    })
  },
  getCourseListDistributeTeacher(searchForm) {
    return request({
      url: '/course/getCourseListDistributeTeacher',
      method: 'get',
      params: searchForm
    })
  },
  distributeCourse(data) {
    return request({
      url: '/teacherCourse/distributeCourse',
      method: 'post',
      data
    })
  },
  withdrawCourse(data) {
    return request({
      url: '/teacherCourse/withdrawCourse',
      method: 'delete',
      data
    })
  },
  getStudentList(searchForm) {
    return request({
      url: '/student/getStudentList',
      method: 'get',
      params: searchForm
    })
  },
  getStudent(studentId) {
    return request({
      url: `/student/getStudent?studentId=${studentId}`,
      method: 'get'
    })
  },
  updateStudent(data) {
    return request({
      url: '/student/updateStudent',
      method: 'put',
      data
    })
  },
  addStudent(data) {
    return request({
      url: '/student/addStudent',
      method: 'post',
      data
    })
  },
  saveStudent(data) {
    if (data.id === null || data.id === undefined) {
      return this.addStudent(data);
    } else {
      return this.updateStudent(data);
    }
  },
  deleteStudent(data) {
    return request({
      url: '/student/deleteStudent',
      method: 'delete',
      data
    })
  }
}
