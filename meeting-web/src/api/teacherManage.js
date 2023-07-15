/**
 *   @author KeFan
 *   @date 2023/6/2
 *   @time 9:59
 */

import request from '@/utils/request'

export default {
  getStudentList(searchForm) {
    return request({
      url: '/student-team/getStudentAllInf',
      method: 'get',
      params: searchForm
    })
  },
  getStudentLogs(mobilephone) {
    return request({
      url: '/student-log/getStudentLogList',
      method: 'get',
      params: {mobilephone}
    })
  },
  updateStudentLog(data) {
    return request({
      url: '/student-log/updateStudentLog',
      method: 'put',
      data
    });
  },
  getTeamLogs(studentId) {
    return request({
      url: '/team-log/getTeamLogList',
      method: 'get',
      params: {studentId}
    })
  },
  updateTeamLog(data) {
    return request({
      url: '/team-log/updateTeamLog',
      method: 'put',
      data
    })
  },
}
