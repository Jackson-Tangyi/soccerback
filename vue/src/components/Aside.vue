<template>
  <el-menu :default-openeds="['1']" style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(48, 65, 86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
      <b style="color: white" v-show="logoTextShow">Info-System</b>
    </div>

    <el-menu-item index="/home">
      <template slot="title">
        <i class="el-icon-location-information"></i>
        <span slot="title">主页</span>
      </template>
    </el-menu-item>

    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title">System Info</span>
      </template>
      <el-menu-item index="/user">
        <template>
          <i class="el-icon-s-custom"></i>
          <span>User</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/coach">
        <template>
          <i class="el-icon-s-check"></i>
          <span>Coach</span>
        </template>
      </el-menu-item>

      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-s-custom"></i>
          <span>Player</span>
        </template>
        <el-menu-item index="/player">
          <template>
            <i class="el-icon-s-custom"></i>
            <span>PlayerInfo</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/playerChart">
          <template>
            <i class="el-icon-s-data"></i>
            <span>PlayerChart</span>
          </template>
        </el-menu-item>
      </el-submenu>
    </el-submenu>

    <el-submenu index="3">
      <template slot="title">
        <i class="el-icon-loading"></i>
        <span slot="title">Game Info</span>
      </template>
      <el-menu-item index="/game">
        <i class="el-icon-coordinate"></i>
        <span>Game</span>
      </el-menu-item>
    </el-submenu>

    <el-submenu index="4">
      <template slot="title">
        <i class="el-icon-scissors"></i>
        <span slot="title">Treatment</span>
      </template>
      <el-menu-item index="/doctor">
        <i class="el-icon-user"></i>
        <span>Doctor</span>
      </el-menu-item>

      <el-submenu index="5">
        <template slot="title">
          <i class="el-icon-s-custom"></i>
          <span>Cure</span>
        </template>
        <el-menu-item index="/cure">
          <i class="el-icon-school"></i>
          <span>CureInfo</span>
        </el-menu-item>
        <el-menu-item index="/cureChart">
          <i class="el-icon-pie-chart"></i>
          <span>CureChart</span>
        </el-menu-item>
      </el-submenu>
    </el-submenu>

    <el-submenu index="6">
      <template slot="title">
        <i class="el-icon-office-building"></i>
        <span slot="title">Office</span>
      </template>
      <el-menu-item index="/director">
        <i class="el-icon-monitor"></i>
        <span>Director</span>
      </el-menu-item>
      <el-menu-item index="/file">
        <i class="el-icon-monitor"></i>
        <span>File</span>
      </el-menu-item>
    </el-submenu>

  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse: Boolean,
    logoTextShow: Boolean
  }
}
</script>

<style scoped>

</style>