<template>
<div>
  <el-row v-for="item in videos" :key="item.id" :gutter="10" style="margin: 10px">
    <el-col :span="4" >
      <img src="../../assets/images.jpg" style="width: 100px;height: 100px" />
    </el-col>
      <el-col :span="20" >
      <el-card>
        <el-divider></el-divider>
          <span class="item" style="font-size: 18px;cursor: pointer" @click="detail(item.id)">{{item.name.substr(0,item.name.indexOf('.'))}}</span>
      </el-card>
    </el-col>
  </el-row>

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
      request.get("/front/videos").then(res=>{
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