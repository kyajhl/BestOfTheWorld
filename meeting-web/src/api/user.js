import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: {token}
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

// 用户更新个人信息
export function updateInformation(user, roleId) {
  // 判断角色的类型
  if (roleId === '1') {
    return request({
      url: '/student/updateInformation',
      method: 'put',
      data: user
    })
  } else if (roleId === '2') {
    return request({
      url: '/teacher/updateInformation',
      method: 'put',
      data: user
    })
  } else {
    return request({
      url: '/admin/updateInformation',
      method: 'put',
      data: user
    })
  }
}
