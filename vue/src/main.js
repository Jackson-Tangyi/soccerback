import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import store from './store'
import './assets/gloable.css'
import request from "@/utils/request";//使request变成全局，不用单独导入


Vue.use(ElementUI,{size:"small"})

Vue.config.productionTip = false
Vue.prototype.request=request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
