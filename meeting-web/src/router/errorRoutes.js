/**
 *   @author KeFan
 *   @date 2023/7/16
 *   @time 17:15
 */

const errorRoutes = [
  // 404 page must be placed at the end !!!
  {
    path: '*',
    redirect: '/404',
    hidden: true
  }
];
export default errorRoutes;
