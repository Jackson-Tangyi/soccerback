import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/404',
    name: '404',
    component: () => import('../views/404.vue')
  },{
    path: '/login',
    name: 'Login',
    component: ()=>import('../views/Login.vue')
  },{
    path: '/register',
    name: 'Register',
    component:()=>import('../views/Register.vue')
  },{
    path: '/front',
    name: 'Front',
    component:()=>import('../views/front/Front.vue'),
    children:[
      {path:'home',name:'FrontHome',component:()=>import('../views/front/Home.vue')},
      {path:'video',name:'FrontVideo',component:()=>import('../views/front/Video.vue')},
      {path:'videoDetail',name:'FrontVideoDetail',component:()=>import('../views/front/VideoDetail.vue')},
      {path:'history',name:'FrontHistory',component:()=>import('../views/front/History.vue')},
      {path:'members',name:'FrontMembers',component:()=>import('../views/front/Members.vue')},
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 提供一个重置路由的方法
export const resetRouter = () => {
  router.matcher = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}

export const setRoutes=()=>{
  const storeMenus=localStorage.getItem("menus");
  if(storeMenus){

    //获取当前的路由对象名称数组
    const currentRouteNames=router.getRoutes().map(v=>v.name)
    if(!currentRouteNames.includes("Layout")){//如果这个里面不包含主界面的组件，那么需要拼装路由
      const layoutRoute={
        path: '/', name: 'Layout', component: () => import('../views/Layout.vue'), redirect: "/home", children: [
          { path: 'person', name: 'Person', component: () => import('../views/Person.vue')},
          { path: 'password', name: 'Password', component: () => import('../views/Password.vue')}
        ]
      }
      const menus=JSON.parse(storeMenus)
      menus.forEach(item=>{
        if(item.path){//仅当path不为空才设置路由
          let itemMenu={ path: item.path.replace("/",""), name: item.name, component: () => import('../views/'+item.pagePath+'.vue')}
          layoutRoute.children.push(itemMenu)
        }else if(item.children.length){
          item.children.forEach(item=>{
            if(item.path){
              let itemMenu={ path: item.path.replace("/",""), name: item.name, component: () => import('../views/'+item.pagePath+'.vue')}
              layoutRoute.children.push(itemMenu)
            }
          })
        }
      })
      // 动态添加到现在的路由对象中去
      router.addRoute(layoutRoute)
    }
  }
}

// 重置我就再set一次路由
setRoutes()

// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
  store.commit("setPath")  // 触发store的数据更新

  // 未找到路由的情况
  if (!to.matched.length) {
    const storeMenus = localStorage.getItem("menus")
    if (storeMenus) {
      next("/404")
    } else {
      // 跳回登录页面
      next("/login")
    }
  }
  // 其他的情况都放行
  next()
})

export default router
