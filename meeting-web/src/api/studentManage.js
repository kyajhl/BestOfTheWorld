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
  addLogs(logsForm) {
    return request({
      url: `/student-log/addStudentLog?mobilephone=${logsForm.mobilephone}&content=${logsForm.logsContent}`,
      method: 'post',
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
      params: teamId
    })
  }
}
