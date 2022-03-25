<template>
<div style="padding-left: 200px;padding-right: 200px">
  <el-card>
    <div v-for="item in videos" :key="item.id" style="margin: 10px 0;padding: 10px;color: #666;border-bottom: 1px dashed #ccc">
      <span style="font-size: 18px;cursor: pointer" class="item" @click="detail(item.id)">{{item.name}}</span>
      <span style="float: right;font-size: 12px">File Size : {{item.size}} kb</span>
    </div>
  </el-card>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Video",
  data(){
    return{
      videos:[]
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/file/videos").then(res=>{
        console.log(res)
        this.videos=res.data
      })
    },
    detail(id){
      this.$router.push({path:"/front/videoDetail",query:{id:id}})//路由传参
    }
  }

}
</script>

<style scoped>
.item:hover{
  color: #409EFF;
}
</style>