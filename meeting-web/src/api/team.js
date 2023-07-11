import request from '@/utils/request'

export default {
  getTeamList(searchForm) {
    return request({
      url: '/team/getTeamList',
      method: 'get',
      params: searchForm
    })
  },
  getTeam(teamId) {
    return request({
      url: `/team/getTeamById?teamId=${teamId}`,
      method: 'get'
    })
  },
  updateTeam(data) {
    return request({
      url: '/team/updateTeam',
      method: 'put',
      data
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
      return this.addTeam(data);
    } else {
      return this.updateTeam(data);
    }
  },
  deleteTeam(teamId) {
    return request({
      url: '/tean/deleteTeam',
      method: 'delete',
      params: {teamId}
    })
  }
}
