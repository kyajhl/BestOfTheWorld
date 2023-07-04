const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  roleId: state => state.user.roleId,
  realName: state => state.user.realName,
  menuList: state => state.user.menuList,
  major: state => state.user.major,
  majorClass: state => state.user.majorClass,
  telephone: state => state.user.telephone,
  sex: state => state.user.sex,
  birthday: state => state.user.birthday,
  password: state => state.user.password,

  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  permission_routes: state => state.permission.routes
};
export default getters
