import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: ()=>import('../layout/Layout'),
    redirect:'/login',
    children:[
      {path:'home',name:'HomePage',component:()=>import('../views/Home.vue')},
      {path:'user',name: 'UserManage',component:()=>import('../views/User.vue')},
      {path:'coach',name: 'CoachInfo',component:()=>import('../views/Coach.vue')},
      {path:'game',name: 'GameInfo',component:()=>import('../views/Game.vue')},
      {path:'player',name: 'PlayerInfo',component:()=>import('../views/Player.vue')},
      {path:'person',name:'Person',component:()=>import('../views/Person.vue')},
      {path:'doctor',name: 'Doctor',component:()=>import('../views/Doctor.vue')},
      {path:'director',name: 'Director',component:()=>import('../views/Director.vue')},
      {path:'cure',name:'Cure',component:()=>import('../views/Cure.vue')},
      {path:'cureChart',name: 'CureChart',component:()=>import('../views/CureChart.vue')},
      {path:'password',name: 'Password',component:()=>import('../views/Password.vue')},
      {path: 'file',name: 'File',component:()=>import('../views/File.vue')},
      {path: 'playerChart',name: 'PlayerChart',component:()=>import('../views/PlayerChart.vue')}
    ]
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  },{
    path: '/login',
    name: 'Login',
    component: ()=>import('../views/Login.vue')
  },{
    path: '/register',
    name: 'Register',
    component:()=>import('../views/Register.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
  store.commit("setPath")  // 触发store的数据更新
  next()  // 放行路由
})

export default router
