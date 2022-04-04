<template xmlns:el-col="http://www.w3.org/1999/html">
  <div>
    <div>
        <el-timeline style="padding-top:50px">
          <el-timeline-item
              v-for="activity in activities"
              :key="activity.id"
              :icon="activity.icon"
              :type="activity.type"
              :color="activity.color"
              size="large"
              :timestamp="activity.createTime">
            <el-card>
              <el-col :span="12">
                <template>
                  <el-image
                      style="width: 400px; height: 250px"
                      :src="activity.url"
                      :preview-src-list="[activity.url]">
                  </el-image>
                </template>
                <el-divider direction="horizontal"></el-divider>
              </el-col>

              <el-col :span="12">
                <p style="font-size: 20px">{{activity.content}}</p>
              </el-col>
            </el-card>
          </el-timeline-item>
        </el-timeline>
    </div>

  </div>


</template>

<script>
import request from "@/utils/request";

export default {
  name: "History",
  data(){
    return{
      activities: []
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/front/findAll").then(res=>{
          console.log(res)
        this.activities=res.data
      })
    }
  }
}
</script>

<style scoped>

</style>