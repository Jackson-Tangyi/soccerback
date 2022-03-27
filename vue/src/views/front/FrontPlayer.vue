<template>
  <div style="padding-right: 150px;padding-left: 150px">
      <h1> Forwards </h1>
    <el-divider></el-divider>
    <el-row :gutter="10">
      <el-col :span="6" v-for="item in Forwards">
        <el-card style="margin: 10px">
          <img :src="item.image" style="width: 200px;height:200px;margin: 15px"/>
          <el-divider></el-divider>
          {{item.name}}
        </el-card>
      </el-col>
    </el-row>

    <el-divider></el-divider>
    <h1> Midfielders </h1>
    <el-divider></el-divider>
    <el-row :gutter="10" >
      <el-col :span="6" v-for="item in Midfielders">
        <el-card style="margin: 10px">
          <img :src="item.image" style="width: 200px;height:200px;margin: 15px"/>
          <el-divider></el-divider>
          {{item.name}}
        </el-card>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <h1>Defenders</h1>
    <el-divider></el-divider>
    <el-row :gutter="10" >
      <el-col :span="6" v-for="item in Defenders">
        <el-card style="margin: 10px">
          <img :src="item.image" style="width: 200px;height:200px;margin: 15px"/>
          <el-divider></el-divider>
          {{item.name}}
        </el-card>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <h1>Goalkeepers</h1>
    <el-divider></el-divider>
    <el-row :gutter="10" >
      <el-col :span="6" v-for="item in Goalkeepers">
        <el-card style="margin: 10px">
          <img :src="item.image" style="width: 200px;height:200px;margin: 15px"/>
          <el-divider></el-divider>
          {{item.name}}
        </el-card>
      </el-col>
    </el-row>
  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "FrontPlayer",
  data(){
    return{
      Forwards:[],
      Midfielders:[],
      Defenders:[],
      Goalkeepers:[]
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/front/findPlayers").then(res=>{
        console.log(res)
        this.Forwards=res.data.filter(v=>v.flag === 'Forward')
        this.Midfielders=res.data.filter(v=>v.flag === 'Midfielder')
        this.Defenders=res.data.filter(v=>v.flag === 'Defender')
        this.Goalkeepers=res.data.filter(v=>v.flag === 'Goalkeeper')
      })
    }
  }
}
</script>

<style scoped>

</style>