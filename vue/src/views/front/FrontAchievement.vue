<template>
  <div style="padding-right: 150px;padding-left: 150px">
    <h1>Italian Cup Champion</h1>
    <span>Total: {{ItalianCup.length}}</span>
    <el-divider></el-divider>
    <el-row :gutter="10" >
      <el-col :span="6" v-for="item in ItalianCup">
        <el-card style="margin: 10px">
          <img :src="item.image" style="width: 200px;height:200px;margin: 15px"/>
          <el-divider></el-divider>
          {{item.name}}
          {{item.season}}
        </el-card>
      </el-col>
    </el-row>

    <el-divider></el-divider>
    <h1>Italian Super Cup Champion</h1>
    <span>Total: {{ItalianSuperCup.length}} </span>
    <el-divider></el-divider>
    <el-row :gutter="10" >
      <el-col :span="6" v-for="item in ItalianSuperCup">
        <el-card style="margin: 10px">
          <img :src="item.image" style="width: 200px;height:200px;margin: 15px"/>
          <el-divider></el-divider>
          {{item.name}}
          {{item.season}}
        </el-card>
      </el-col>
    </el-row>

    <el-divider></el-divider>
    <h1>European Championship Cup Champion</h1>
    <span>Total: {{EuropeanChampionshipCup.length}}</span>
    <el-divider></el-divider>
    <el-row :gutter="10" >
      <el-col :span="6" v-for="item in EuropeanChampionshipCup">
        <el-card style="margin: 10px">
          <img :src="item.image" style="width: 200px;height:200px;margin: 15px"/>
          <el-divider></el-divider>
          {{item.name}}
          {{item.season}}
        </el-card>
      </el-col>
    </el-row>

    <el-divider></el-divider>
    <h1>Intercontinental Cup Champion</h1>
    <span>Total: {{EuropeanChampionshipCup.length}}</span>
    <el-divider></el-divider>
    <el-row :gutter="10" >
      <el-col :span="6" v-for="item in IntercontinentalCup">
        <el-card style="margin: 10px">
          <img :src="item.image" style="width: 200px;height:200px;margin: 15px"/>
          <el-divider></el-divider>
          {{item.name}}
          {{item.season}}
        </el-card>
      </el-col>
    </el-row>

    <el-divider></el-divider>
    <h1>Club World Cup Champion</h1>
    <span>Total: {{EuropeanChampionshipCup.length}}</span>
    <el-divider></el-divider>
    <el-row :gutter="10" >
      <el-col :span="6" v-for="item in ClubWorldCup">
        <el-card style="margin: 10px">
          <img :src="item.image" style="width: 200px;height:200px;margin: 15px"/>
          <el-divider></el-divider>
          {{item.name}}
          {{item.season}}
        </el-card>
      </el-col>
    </el-row>

    <el-divider></el-divider>
    <h1> Italian League Cup Champion </h1>
    <span>Total: {{ItalianLeagueCup.length}}</span>
    <el-divider></el-divider>
    <el-row :gutter="10" >
      <el-col :span="6" v-for="item in ItalianLeagueCup">
        <el-card style="margin: 10px">
          <img :src="item.image" style="width: 200px;height:200px;margin: 15px"/>
          <el-divider></el-divider>
          {{item.name}}
          {{item.season}}
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Achievement",
  data(){
    return{
      ItalianCup:[],
      ItalianLeagueCup:[],
      ItalianSuperCup:[],
      EuropeanChampionshipCup:[],
      IntercontinentalCup:[],
      ClubWorldCup:[]

    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/front/findAllCups").then(res=>{
        console.log(res)
        this.ItalianCup=res.data.filter(v=>v.name === 'Italian Cup Champion')
        this.ItalianLeagueCup=res.data.filter(v=>v.name === 'Italian League Cup Champion')
        this.ItalianSuperCup=res.data.filter(v=>v.name === 'Italian Super Cup Champion')
        this.EuropeanChampionshipCup=res.data.filter(v=>v.name === 'European Championship Cup Champion')
        this.ClubWorldCup=res.data.filter(v=>v.name === 'Club World Cup Champion')
        this.IntercontinentalCup=res.data.filter(v=>v.name === 'Intercontinental Cup Champion')
      })
    }
  }
}
</script>

<style scoped>

</style>