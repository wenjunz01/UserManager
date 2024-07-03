import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home',  // 重定向到主页
    children: [
      { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/AdminPerson') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'notice', name: 'Notice', meta: { name: '公告信息' }, component: () => import('../views/manager/Notice') },
      { path: 'tripartite', name: 'Tripartite', meta: { name: '第三方信息' }, component: () => import('../views/manager/Tripartite') },
      { path: 'goods', name: 'Goods', meta: { name: '产品信息' }, component: () => import('../views/manager/Goods') },
      { path: 'contract', name: 'Contract', meta: { name: '合同信息' }, component: () => import('../views/manager/Contract') },
      { path: 'customer', name: 'Customer', meta: { name: '用户信息' }, component: () => import('../views/manager/Customer') },
      { path: 'publiccustomer', name: 'PublicCustomer', meta: { name: '用户公海' }, component: () => import('../views/manager/PublicCustomer') },
      { path: 'clue', name: 'Clue', meta: { name: '线索' }, component: () => import('../views/manager/Clue') },
      { path: 'plan', name: 'Plan', meta: { name: '跟进计划' }, component: () => import('../views/manager/Plan') },
      { path: 'followup', name: 'FollowUp', meta: { name: '跟进记录' }, component: () => import('../views/manager/FollowUp') },
      { path: 'followanalysis', name: 'FollowAnalysis', meta: { name: '跟进分析' }, component: () => import('../views/manager/FollowAnalysis') },
      { path: 'sale', name: 'Sale', meta: { name: '订单信息' }, component: () => import('../views/manager/Sale') },
      { path: 'department', name: 'Department', meta: { name: '部门信息' }, component: () => import('../views/manager/Department') },
      { path: 'staff', name: 'Staff', meta: { name: '员工信息' }, component: () => import('../views/manager/Staff') },
      { path: 'logs', name: 'Logs', meta: { name: '操作日志' }, component: () => import('../views/manager/Logs') },
      { path: 'staffPerson', name: 'StaffPerson', meta: { name: '员工个人信息' }, component: () => import('../views/manager/StaffPerson') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
