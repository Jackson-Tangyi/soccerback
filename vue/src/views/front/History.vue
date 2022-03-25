<template xmlns:el-col="http://www.w3.org/1999/html">
  <div style="position: center">
    <div style="position: center;padding-left: 200px;padding-right: 200px">
        <el-timeline style="padding-top:50px">
          <el-timeline-item
              v-for="activity in activities"
              :key="activity.id"
              :icon="activity.icon"
              :type="activity.type"
              :color="activity.color"
              :timestamp="activity.createTime">
            <el-card style="min-height: 200px">
              <el-col :span="12">
                <template>
                  <el-image
                      style="width: 400px; height: 400px"
                      :src="activity.url"
                      :preview-src-list="[activity.url]">
                  </el-image>
                </template>
                <el-divider direction="horizontal"></el-divider>
              </el-col>

              <el-col :span="12">
                {{activity.content}}
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
      request.get("/history/findAll").then(res=>{
          console.log(res)
        this.activities=res.data
      })
    }
  }
}
</script>

<style scoped>

</style>