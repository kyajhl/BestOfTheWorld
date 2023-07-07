import {login, logout, getInfo, register, getRoleId} from '@/api/user'
import {getToken, setToken, removeToken} from '@/utils/auth'
import {resetRouter} from '@/router'
import router from '@/router'
import {Message} from 'element-ui'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    menuList: [],
    roleId: '',
    realName: '',
    major: '',
    majorClass: '',
    telephone: '',
    sex: '',
    birthday: '',
    password: ''
  }
};

const state = getDefaultState();

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_MENULIST(state, menuList) {
    state.menuList = menuList;
  },
  SET_ROLEID(state, roleId) {
    state.roleId = roleId;
  },
  SET_REALNAME(state, realName) {
    state.realName = realName;
  },
  SET_MAJOR(state, major) {
    state.major = major;
  },
  SET_MAJORCLASS(state, majorClass) {
    state.majorClass = majorClass;
  },
  SET_TELEPHONE(state, telephone) {
    state.telephone = telephone;
  },
  SET_SEX(state, sex) {
    state.sex = sex;
  },
  SET_BIRTHDAY(state, birthday) {
    state.birthday = birthday;
  },
  SET_PASSWORD(state, password) {
    state.password = password;
  }
};

const actions = {
  // user login
  login({commit}, userInfo) {
    const {username, password, radioCheck} = userInfo;
    return new Promise((resolve, reject) => {
      login({username: username.trim(), password: password, radioCheck: radioCheck}, radioCheck).then(response => {
        const {data} = response;
        commit('SET_TOKEN', data.token);
        // 把 token 存入 cookie
        setToken(data.token);
        resolve();
      }).catch(error => {
        reject(error);
      })
    })
  },

  // get user roleId
  getRoleId({commit, state}) {
    return new Promise((resolve, reject) => {
      getRoleId(state.token).then(
        response => {
          commit('SET_ROLEID', response.data.roleId);
          resolve();
        }
      ).catch(error => {
        reject(error);
      })
    })
  },

  // get user info
  getInfo({commit, state}) {

    console.log("state.roleId：", state.roleId);

    return new Promise((resolve, reject) => {
      getInfo(state.token, state.roleId).then(response => {
        const {data} = response;

        if (!data) {
          reject('响应数据为空，请重新登录');
        }

        const {name, avatar, roleId, realName, major, majorClass, telephone, sex, birthday, password, menuList} = data;

        commit('SET_NAME', name);
        commit('SET_AVATAR', avatar);
        commit('SET_ROLEID', roleId);
        commit('SET_REALNAME', realName);
        commit('SET_MAJOR', major);
        commit('SET_MAJORCLASS', majorClass);
        commit('SET_TELEPHONE', telephone);
        commit('SET_SEX', sex);
        commit('SET_BIRTHDAY', birthday);
        commit('SET_PASSWORD', password);

        commit('SET_MENULIST', menuList);

        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({commit}) {
    return new Promise((resolve, reject) => {
      logout().then(() => {
        removeToken(); // 删除 token
        resetRouter(); // 重置路由器
        commit('RESET_STATE'); // 把 state 还原，防止下次登陆有数据
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({commit}) {
    return new Promise(resolve => {
      removeToken(); // must remove  token  first
      commit('RESET_STATE');
      resolve()
    })
  },

  // user register
  register({commit}, user) {
    const {username, password, radioCheck} = user;
    return new Promise((resolve, reject) => {
      register({username: username.trim(), password: password, radioCheck: radioCheck}, radioCheck).then(
        response => {
          Message.success(response.msg);
          router.push('/login');
          resolve();
        },
        error => {
          Message.error("手机号存在，注册失败");
        }
      ).catch(error => {
        reject(error);
      })
    })
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

