/**
 *   @author KeFan
 *   @date 2023/6/2
 *   @time 9:59
 */

import request from '@/utils/request'

export default {
  getTeachingCourseList(searchForm) {
    return request({
      url: '/teacherCourse/getTeachingCourseList',
      method: 'get',
      params: searchForm
    })
  },
  getTeachingCourseListModifyStudent(searchForm) {
    return request({
      url: '/teacherCourse/getTeachingCourseListModifyStudent',
      method: 'get',
      params: searchForm
    })
  },
  getStudentList(searchForm) {
    return request({
      url: '/teacherCourse/getStudentList',
      method: 'get',
      params: searchForm
    })
  },

}
