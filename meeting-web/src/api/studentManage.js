/**
 *   @author KeFan
 *   @date 2023/5/31
 *   @time 16:19
 */

import request from '@/utils/request'

export default {
  getTeamList(searchForm) {
    return request({
      url: '/student-team/getTeamListByMobilephone',
      method: 'get',
      params: searchForm
    })
  },
  addPersonLogs(logsForm) {
    return request({
      url: `/student-log/addStudentLog?mobilephone=${logsForm.mobilephone}&content=${logsForm.logsContent}`,
      method: 'post'
    })
  },
  addTeamLogs(logsForm) {
    return request({
      url: `/team-log/addTeamLog?content=${logsForm.logsContent}&teamId=${logsForm.teamId}`,
      method: 'post'
    })
  },
  addSummary(summaryForm) {
    return request({
      url: '/summarize/addSummarize',
      method: 'post',
      data: summaryForm
    })
  },
  getTeamInformation(teamId) {
    return request({
      url: '/student-team/getTeamInformation',
      method: 'get',
      params: {teamId}
    })
  },
  getMessageList() {
    return request({
      url: '/message/getMessageList',
      method: 'get'
    })
  },
  addMessage(data) {
    return request({
      url: '/message/addMessage',
      method: 'post',
      data
    })
  },
  getPersonLogsList(mobilephone) {
    return request({
      url: '/student-log/getStudentLogList',
      method: 'get',
      params: {mobilephone}
    })
  },
  updatePersonLog(data) {
    return request({
      url: '/student-log/updateStudentLog',
      method: 'put',
      data
    })
  },
  deletePersonLog(data) {
    return request({
      url: '/student-log/deleteStudentLog',
      method: 'delete',
      data
    })
  },
  getIdByMobilePhone(mobilephone) {
    return request({
      url: '/student/getIdByMobilePhone',
      method: 'get',
      params: {mobilephone}
    })
  },
  getTeamLogsList(studentId) {
    return request({
      url: '/team-log/getTeamLogList',
      method: 'get',
      params: {studentId}
    })
  },
  getPositionByTeamId(teamId, studentId) {
    return request({
      url: '/student-team/getPositionByTeamId',
      method: 'get',
      params: {
        teamId,
        studentId
      }
    })
  },
  updateTeamLog(data) {
    return request({
      url: '/team-log/updateTeamLog',
      method: 'put',
      data
    })
  },
  deleteTeamLog(data) {
    return request({
      url: '/team-log/deleteTeamLog',
      method: 'delete',
      data
    })
  },
  getSummaryList() {
    return request({
      url: '/summarize/'
    })
  }
}
