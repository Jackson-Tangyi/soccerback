<template>
  <div>
<!--  走马灯展示  -->
    <div style="padding-top: 30px">
      <el-carousel :interval="4000" height="350px" type="card">
        <el-carousel-item v-for="item in pictures" :key="item.id">
          <el-image :src="item.url"></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div>
      <h1>JUVENTUS</h1>
      <el-divider></el-divider>
      <p>Juventus Football Club S.p.A, referred to as Juve, is a football club located in Turin, Piedmont, Italy. Juventus' traditional home kit is a black and white top with white or black shorts. In fact, the team originally used pink as the jersey color and added a black tie, but the jersey supplier mistakenly sent the jersey of the English team Notts County to Juventus. Jersey color.
      </p>
      <el-divider></el-divider>
      <p>Juventus is the first club in history to achieve three major cups (European Champions Clubs Cup, UEFA Cup, European Cup Winners' Cup). The club's original home stadium was located at the Deer Abbey Stadium, and shared the stadium with the same city club Turin Football Club until after the 2005-06 season, Turin moved its home stadium to the Torino Olympic Stadium. Juventus moved its home stadium to Juventus Stadium (now named Allianz Stadium by sponsor Allianz Insurance Company) since the 2011-12 season.
      The match between Juventus and Inter Milan is called the "Italian National Derby", and the two clubs Inter Milan and AC Milan are also called the "Northern Three", and the match between the same city team Turin is the "Derby". Lyndby". Juventus was a founding member of the defunct G-14.
      </p>
      <el-divider></el-divider>
      <p>        Since 1923, Juventus has been owned by the famous Italian family Agnelli, and the current chairman of the club is Andrea Agnelli, the fourth generation of the family.
      </p>

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
      request.get("/front/pic").then(res=>{
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