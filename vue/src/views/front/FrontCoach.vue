<template>
  <div style="padding-right: 150px;padding-left: 150px">
    <h1> Coaches </h1>
    <el-divider></el-divider>
    <el-row :gutter="10">
      <el-col :span="6" v-for="item in coaches" :key="item.id">
        <el-card style="margin: 10px">
          <img :src="item.image" style="width: 200px;height:200px;margin: 15px"/>
          <el-divider></el-divider>
          Name : {{item.name}}
          <el-divider></el-divider>
          Career : {{item.career}}
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "FrontCoach",
  data(){
    return{
      coaches:[]
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/front/findCoaches").then(res=>{
        console.log(res)
        this.coaches=res.data
      })
    }
  }
}
</script>

<style scoped>

</style>