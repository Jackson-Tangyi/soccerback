<template>
  <!-- 加不加div取决于整个页面 只能有一个根div 去包含所有 -->
  <div style="line-height: 60px; display: flex">
    <div style="flex: 1">
      <!--     点击触发收缩或者展开函数     -->
      <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px" @click="collapse"></span>

      <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px">
        <el-breadcrumb-item :to="{ path: '/home' }">HomePage</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!--    cursor: pointer 鼠标上去变成一个小手形状    -->
    <el-dropdown style="width: 150px; cursor: pointer;text-align: right">
      <div style="display: inline-block">
       <img :src="user.avatarUrl" alt="" style="width: 30px;border-radius: 50%;position: relative;top:10px;right: 5px"/>
        <span>{{ user.username }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>

      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/password">Change Password</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/person">Personal Info</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0" divided>
         <span style="text-decoration: none;color: red" @click="logout">Logout</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>

</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass:String,
    user:Object
  },
  methods:{
    collapse(){
      // this.$parent.$parent.$parent.$parent.collapse() //通过4个 $parent 组件找到父组件，从而调用其折叠方法

      //通过触发父组件
      this.$emit("asideCollapse")
    },
    logout(){
      this.$store.commit("logout")
      this.$message.success("Logout successfully!")
    }
  },
  computed: {
    currentPathName () {
      return this.$store.state.currentPathName;　　//需要监听的数据
    }
  }

}
</script>

<style scoped>

</style>