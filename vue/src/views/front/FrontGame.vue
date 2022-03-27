<template>
  <div style="padding-left: 100px;padding-right: 100px">
    <h1>Italian Cup</h1>
    <el-card>
      <el-table :data="ItalianCup" border stripe :header-cell-class-name="'headerBg'">
        <el-table-column prop="name" label="Name" ></el-table-column>
        <el-table-column prop="date" label="Date" ></el-table-column>
        <el-table-column prop="homeaway" label="Home/Away" ></el-table-column>
        <el-table-column prop="score" label="Score" ></el-table-column>
        <el-table-column prop="place" label="Place"></el-table-column>
      </el-table>
    </el-card>
    <el-divider></el-divider>
    <h1>Italian League Cup</h1>
    <el-card>
      <el-table :data="ItalianLeagueCup" border stripe :header-cell-class-name="'headerBg'" >
        <el-table-column prop="name" label="Name" ></el-table-column>
        <el-table-column prop="date" label="Date" ></el-table-column>
        <el-table-column prop="homeaway" label="Home/Away" ></el-table-column>
        <el-table-column prop="score" label="Score" ></el-table-column>
        <el-table-column prop="place" label="Place"></el-table-column>
      </el-table>
    </el-card>
    <el-divider></el-divider>
    <h1>Italian Super Cup</h1>
    <el-card>
      <el-table :data="ItalianSuperCup" border stripe :header-cell-class-name="'headerBg'" >
        <el-table-column prop="name" label="Name" ></el-table-column>
        <el-table-column prop="date" label="Date" ></el-table-column>
        <el-table-column prop="homeaway" label="Home/Away"></el-table-column>
        <el-table-column prop="score" label="Score"></el-table-column>
        <el-table-column prop="place" label="Place"></el-table-column>
      </el-table>
    </el-card>
    <el-divider></el-divider>
    <h1>European Championship Cup</h1>
    <el-card>
      <el-table :data="EuropeanChampionshipCup" border stripe :header-cell-class-name="'headerBg'" >
        <el-table-column prop="name" label="Name"></el-table-column>
        <el-table-column prop="date" label="Date"></el-table-column>
        <el-table-column prop="homeaway" label="Home/Away"></el-table-column>
        <el-table-column prop="score" label="Score"></el-table-column>
        <el-table-column prop="place" label="Place"></el-table-column>
      </el-table>
    </el-card>

  </div>


</template>

<script>
import request from "@/utils/request";

export default {
  name: "FrontGame",
  data(){
    return{
      ItalianCup:[],
      ItalianLeagueCup:[],
      ItalianSuperCup:[],
      EuropeanChampionshipCup:[],

    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/front/findAllGames",).then(res=>{
            console.log(res)
            this.ItalianCup=res.data.filter(v=>v.type === 'Italian Cup')
            this.ItalianLeagueCup=res.data.filter(v=>v.type === 'Italian League Cup')
            this.ItalianSuperCup=res.data.filter(v=>v.type === 'Italian Super Cup')
            this.EuropeanChampionshipCup=res.data.filter(v=>v.type === 'European Championship Cup')

          }
      )
    }
  }
}
</script>

<style scoped>

</style>