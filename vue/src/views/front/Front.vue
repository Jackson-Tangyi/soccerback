<template>
  <div>
    <!--    头部-->
    <div style="display: flex; height: 60px; line-height: 60px; border-bottom: 1px solid #eee">
      <div style="width: 300px; display: flex; padding-left: 30px">
        <div style="width: 60px">
          <img alt="" src="../../assets/lg.png" style="width: 50px; position: relative; top: 5px; right: 0">
        </div>
        <div style="flex: 1">Welcome to JUVENTUS</div>
      </div>
      <div style="flex: 1">

        <!--        导航菜单-->
        <el-menu
            active-text-color="#ffd04b"
            background-color="black"
            class="el-menu-demo"
            mode="horizontal"
            router
            text-color="#fff">
          <el-menu-item index="/front/home" style="width: 150px;padding-left: 60px">Home</el-menu-item>
          <el-menu-item index="/front/frontNews" style="width: 150px;padding-left: 60px">News</el-menu-item>
          <el-submenu index="3" style="width: 150px;padding-left: 60px">
            <template slot="title">Game</template>
            <el-menu-item index="/front/frontGame" >
              Game Schedule
            </el-menu-item>
            <el-menu-item index="/front/video" >
              Game Video
            </el-menu-item>
          </el-submenu>
          <el-submenu index="1" style="width: 150px;padding-left: 60px">
            <template slot="title">Club</template>
            <el-menu-item index="/front/history" >
              History
            </el-menu-item>
            <el-menu-item index="/front/achievement" >
              Achievement
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2" style="width: 200px;padding-left: 60px">
            <template slot="title">About Team</template>
            <el-menu-item index="/front/frontPlayer" >
              Player
            </el-menu-item>
            <el-menu-item index="/front/frontCoach" >
              Coach
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </div>

<!--   右边展示的内容   -->
      <div style="width: 200px">
        <div v-if="!user.username" style="text-align: right; padding-right: 30px">
          <el-button @click="$router.push('/login')">Login</el-button>
          <el-button @click="$router.push('/register')">Register</el-button>
        </div>
        <div v-else>
          <el-dropdown style="width: 150px; cursor: pointer; text-align: right">
            <div style="display: inline-block">
              <img :src="user.avatarUrl" alt=""
                   style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
              <span>{{ user.username }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            </div>
            <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
              <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                <router-link to="/front/password">Password</router-link>
              </el-dropdown-item>
              <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                <router-link to="/front/person">Personal Info</router-link>
              </el-dropdown-item>
              <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                <span style="text-decoration: none" @click="logout">Logout</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>

<!-- 页面展示内容  -->
    <div style="width: 1200px; margin: 0 auto">
      <router-view />
    </div>
  </div>
</template>

<script>

export default {
  name: "Front",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {

  },
  methods: {
    logout() {
      this.$store.commit("logout")
      this.$message.success("Logout Successfully")
    }
  }
}
</script>

<style scoped>
.item{
  display: inline-block;
  width: 100px;

  text-align: center;
  cursor: pointer
}
.item a {
  color: 	#1E90FF;
}
.item:hover{
  background-color: 	LightPink;
}
</style>