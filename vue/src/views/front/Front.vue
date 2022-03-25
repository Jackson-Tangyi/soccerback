<template>
  <el-container  style="min-height: 100vh">
    <el-header>
      <el-row>
        <el-col :span="2">
          <div style="height: 60px; line-height: 60px; text-align: center">
            <a href="/front/home"><img src="../../assets/lg.png" style="width: 60px; position: relative; margin-right: 5px"></a>
          </div>
        </el-col>
        <el-col :span="2">
          <div style="height: 60px; line-height: 60px; text-align: center">
            <b style="color: black;float: left">JUVENTUS</b>
          </div>
        </el-col>
        <el-col :span="20">
          <el-menu
              class="el-menu-demo"
              mode="horizontal"
              background-color="black"
              text-color="#fff"
              router
              active-text-color="#ffd04b">
            <el-menu-item index="/front/home" style="width: 150px;padding-left: 60px">Home</el-menu-item>
            <el-menu-item index="/front/video" style="width: 150px;padding-left: 60px">Video</el-menu-item>
            <el-submenu index="1" style="width: 150px;padding-left: 60px">
              <template slot="title">Club</template>
              <el-menu-item index="/front/history" >
                        History
              </el-menu-item>
            </el-submenu>
            <el-submenu index="2" style="width: 200px;padding-left: 60px">
              <template slot="title">About Team</template>
              <el-menu-item index="/front/members" >
                Team members
              </el-menu-item>
            </el-submenu>
            <el-menu-item index="../login" style="float: right;padding-left: 40px">
              BACKSTAGE ENTRANCE
            </el-menu-item>
          </el-menu>
        </el-col>
      </el-row>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>

export default {
  name: "Front",
}
</script>

<style scoped>
.el-header{
  padding: 0 0px;
}
.el-main{
  text-align: center;
}
.login_text:hover{
  color: #409EFF;
}
</style>