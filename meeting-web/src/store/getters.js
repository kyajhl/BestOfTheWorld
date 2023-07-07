const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  roleId: state => state.user.roleId,
  major: state => state.user.major,
  majorClass: state => state.user.majorClass,
  mobilephone: state => state.user.mobilephone,
  gender: state => state.user.gender,
  birthday: state => state.user.birthday,
  password: state => state.user.password,
  college: state => state.user.college,
  studentId: state => state.user.studentId,
  studentName: state => state.user.studentName,

  teacherName: state => state.user.teacherName,

  enterpriseName: state => state.user.enterpriseName,
  email: state => state.user.email,

  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  permission_routes: state => state.permission.routes
};
export default getters
