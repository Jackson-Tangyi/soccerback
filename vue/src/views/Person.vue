<template>
  <el-card style="width: 500px;">
    <el-form label-width="80px" size="small">
      <el-upload
          class="avatar-uploader"
          action="http://localhost:9090/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
      >
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>

      <el-form-item label="用户名">
        <el-input v-model="form.username" disabled autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-input v-model="form.sex" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model="form.age" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Person",
  data(){
      return{
        form:{},
        user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
      }
  },
  created(){
    this.getUser().then(res => {
      console.log(res)
      this.form = res
    })
  },
  methods:{
    save(){
      request.post("/user",this.form).then(res=>{
        if(res.code ==='200'){

          this.$message.success("Update successfully! Please refresh!")
          // 触发父级更新User的方法
          this.$emit("refreshUser")

          // 更新浏览器存储的用户信息
          this.getUser().then(res => {
            res.token = JSON.parse(localStorage.getItem("user")).token
            localStorage.setItem("user", JSON.stringify(res))
          })
        }else {
          this.$message.error("Failed!")

        }
      })
    },
    async getUser(){//头像改变了了之后需要在Header中更新,使用同步
      return (await request.get("/user/username/"+this.user.username)).data
    },
    handleAvatarSuccess(res){
      this.form.avatarUrl = res
    }
  }
}
</script>

<style>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>