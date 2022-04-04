import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import store from './store'
import './assets/gloable.css'
import request from "@/utils/request";//使request变成全局，不用单独导入

import locale from 'element-ui/lib/locale/lang/en';//element国际化组件

//引入视频组件
import VideoPlayer from 'vue-video-player'
require('video.js/dist/video-js.css')
require('vue-video-player/src/custom-theme.css')
Vue.use(VideoPlayer)

// main.js全局注册 markdown编辑器
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)


Vue.use(ElementUI, {size:"small",locale})

Vue.config.productionTip = false
Vue.prototype.request=request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
