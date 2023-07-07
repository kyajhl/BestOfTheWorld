import request from '@/utils/request'

export function login(user, roleId) {
  if (roleId === '1') {
    return request({
      url: '/student/login',
      method: 'post',
      data: user
    })
  } else if (roleId === '2') {
    return request({
      url: '/teacher/login',
      method: 'post',
      data: user
    })
  } else {
    return request({
      url: '/enterprise/login',
      method: 'post',
      data: user
    })
  }
}

export function register(user, roleId) {
  if (roleId === '1') {
    return request({
      url: '/student/register',
      method: 'post',
      data: user
    })
  } else if (roleId === '2') {
    return request({
      url: '/teacher/register',
      method: 'post',
      data: user
    })
  } else {
    return request({
      url: '/enterprise/register',
      method: 'post',
      data: user
    })
  }
}

export function getRoleId(token) {
  return request({
    url: '/user/getRoleId',
    method: 'get',
    params: {token}
  })
}

export function getInfo(token, roleId) {
  if (roleId === '1') {
    return request({
      url: '/student/info',
      method: 'get',
      params: {token}
    })
  } else if (roleId === '2') {
    return request({
      url: '/teacher/info',
      method: 'get',
      params: {token}
    })
  } else {
    return request({
      url: '/enterprise/info',
      method: 'get',
      params: {token}
    })
  }
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
      url: '/enterprise/updateInformation',
      method: 'put',
      data: user
    })
  }
}
