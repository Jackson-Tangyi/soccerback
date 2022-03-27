<template>
  <div class="center-in-center">
    <el-card style="width: 500px;">
      <el-form label-width="150px" :model="form" :rules="rules" ref="pass">

        <el-form-item label="Password" prop="password">
          <el-input v-model="form.password" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item label="New Password" prop="newPassword">
          <el-input v-model="form.newPassword" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item label="Confirm Password" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="save">Save</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>

</template>

<script>
export default {
  name: "Password",
  data(){
    return{
      form:{},
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      rules: {
        password: [
          { required: true, message: 'Input original password', trigger: 'blur' },
          { min: 3, message: 'Not less than 3 digits long', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: 'Input new password', trigger: 'blur' },
          { min: 3, message: 'Not less than 3 digits long', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: 'Input new password again', trigger: 'blur' },
          { min: 3, message: 'Not less than 3 digits long', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    this.form.username=this.user.username
  },
  methods:{
    save() {
      this.$refs.pass.validate((valid) => {
        if (valid) {
          if (this.form.newPassword !== this.form.confirmPassword) {
            this.$message.error("New passwords are different")
            return false
          }
          this.request.post("/user/password", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("Change successfully")
              this.$store.commit("logout")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
  }
}
</script>

<style scoped>
.center-in-center {
  position: absolute;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  -moz-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  -o-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}
</style>