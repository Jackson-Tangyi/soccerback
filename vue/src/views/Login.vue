<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 400px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>Welcome</b></div>
      <el-form :model="form" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item>
          <div style="display: flex">
            <el-input prefix-icon="el-icon-key" v-model="form.validCode" style="width: 50%;" placeholder="请输入验证码"></el-input>
            <ValidCode @input="createValidCode"/>
          </div>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/register')">Register</el-button>
          <el-button type="primary" size="small"  autocomplete="off" @click="login">Login</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import ValidCode from "@/components/ValidCode";
import {setRoutes} from "@/router";

export default {
  name: "Login",
  components:{
    ValidCode
  },
  data(){
    return{
      form:{},
      rules:{
        username:[
          {required:true,message:'Please input username',trigger:'blur'},
          {min:3,max:10,message: 'Length between 1-20 chars',trigger: 'blur'}
        ],
        password:[
          {required:true,message:'Please input password',trigger:'blur'},
          {min:5,max:20,message: 'Length between 1-20 chars',trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    createValidCode(data){
      this.validCode=data
    },
    login(){
      this.$refs['userForm'].validate((valid)=>{//这里的form对应于上面表单的ref
        if(valid){//表示验证规则合法才会发送下面的请求
          if(!this.form.validCode){//验证码
            this.$message.error("Please input verification code")
            return
          }
          if(this.form.validCode.toLowerCase()!== this.validCode.toLowerCase()){
            this.$message.error("Verification code error")
            return
          }
          request.post("/user/login",this.form).then(res=>{
            if(res.code === '200'){
              localStorage.setItem("user",JSON.stringify(res.data))//存储用户信息到浏览器
              localStorage.setItem("menus",JSON.stringify(res.data.menus))//存储用户权限菜单到浏览器

              // 动态设置当前用户的路由
              setRoutes()
              this.$message.success("Login Success")
              if(res.data.role === 'ROLE_USER'){//如果是普通用户，那么只能在前台
                this.$router.push("/front/home")
              }else{
                this.$router.push("/game")//教练、医生、管理员可以进入后台
              }
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to right, white,black);
  overflow: hidden;
/*超出部分隐藏*/
}
</style>