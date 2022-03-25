<template>
  <div style="padding-left: 150px;padding-right: 150px">
    <div>
      <h1>introduction about team</h1>
    </div>

    <div>
      <h1>比赛信息</h1>
    </div>

<!--  走马灯展示  -->
    <div>
      <el-carousel :interval="4000" type="card" height="400px">
        <el-carousel-item v-for="item in pictures" :key="item.id">
          <span>{{item.name}}</span>
          <el-image :src="item.url"></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>

  </div>

</template>

<script>

import request from "@/utils/request";

export default {
  name: "FrontHome",
  data(){
    return{
      pictures:[]
    }
  },
  methods:{
    load(){
      request.get("/file/pic").then(res=>{
            console.log(res)
          this.pictures=res.data
      })
    }
  },
  created() {
    this.load()
  }
}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

</style>