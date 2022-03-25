<template>
<!-- 加不加div取决于整个页面 只能有一个根div 去包含所有 -->
<div>
  <div style="margin: 10px 0">
    <el-input style="width: 200px" placeholder="Input username" suffix-icon="el-icon-search" v-model="username" ></el-input>
    <el-input style="width: 200px" placeholder="Input email" suffix-icon="el-icon-message" class="ml-5" v-model="email"></el-input>
    <el-input style="width: 200px" placeholder="Input address" suffix-icon="el-icon-position" class="ml-5" v-model="address"></el-input>
    <el-button class="ml-5" type="primary" @click="load">Research</el-button>
    <el-button type="warning" @click="reset">Reset</el-button>
  </div>

  <div style="margin: 10px 0">
    <el-button type="primary" @click="handleAdd" >Add<i class="el-icon-circle-plus-outline"></i></el-button>
    <el-popconfirm
        class="ml-5"
        confirm-button-text='确定'
        cancel-button-text='我再想想'
        icon="el-icon-info"
        icon-color="red"
        title="您确定批量删除这些数据吗？"
        @confirm="delBatch"
    >
      <el-button type="danger" slot="reference">Batch Delete <i class="el-icon-remove-outline"></i></el-button>
    </el-popconfirm>
    <el-upload action="http://localhost:9090/user/import"
               :show-file-list="false"
               accept="xlsx"
               :on-success="handleExcelImportSuccess"
                style="display: inline-block">
      <el-button type="primary" class="ml-5">Import <i class="el-icon-bottom"></i></el-button>
    </el-upload>
    <el-button type="primary" @click="exp" class="ml-5">Export <i class="el-icon-top"></i></el-button>
  </div>

  <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
    <el-table-column prop="username" label="Username" width="140"></el-table-column>
    <el-table-column prop="age" label="Age" width="80" sortable></el-table-column>
    <el-table-column prop="sex" label="Sex" width="80"></el-table-column>
    <el-table-column prop="email" label="Email" width="120"></el-table-column>
    <el-table-column prop="address" label="Address"></el-table-column>
    <el-table-column prop="role" label="Role">
      <template slot-scope="scope">
        <el-tag type="primary" v-if="scope.row.role === 'ROLE_ADMIN'">ADMIN</el-tag>
        <el-tag type="warning" v-if="scope.row.role === 'ROLE_USER'">USER</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="Operations"  width="200" align="center">
      <template slot-scope="scope">
        <el-button type="success" @click="handleEdit(scope.row)">Edit <i class="el-icon-edit"></i></el-button>
        <el-popconfirm
            class="ml-5"
            confirm-button-text='Yes'
            cancel-button-text='Rethink'
            icon="el-icon-info"
            icon-color="red"
            title="Are you sure？"
            @confirm="del(scope.row.id)"
        >
          <el-button type="danger" slot="reference">Delete <i class="el-icon-remove-outline"></i></el-button>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

  <!--    Page Plugin    -->
  <div style="padding: 10px 0">
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 8, 10, 15]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>

  <!-- Edit Dialog -->
  <el-dialog title="User Information" :visible.sync="dialogFormVisible" width="30%" >
    <el-form label-width="80px" size="small">
      <el-form-item label="Username">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Age">
        <el-input v-model="form.age" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Sex">
        <el-select v-model="form.sex" placeholder="请选择">
          <el-option v-for="item in sexs" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Address">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Role">
        <el-select v-model="form.role" placeholder="Select">
          <el-option v-for="item in roleType" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">Cancel</el-button>
      <el-button type="primary" @click="save">Confirm</el-button>
    </div>
  </el-dialog>

</div>



</template>

<script>
import request from "@/utils/request";

export default {
  name: "User",
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      username: "",
      email: "",
      address: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      sexs:[
        {
          value: 'Male',
          label: 'Male'
        }, {
          value: 'Female',
          label: 'Female'
        }],
      roleType:[
        {
          value: 'ROLE_ADMIN',
          label: 'ROLE_ADMIN'
        }, {
          value: 'ROLE_USER',
          label: 'ROLE_USER'
        }
      ]
    }
  },
  created() {
    //请求分页数据
    this.load()
  },
  methods:{
    load(){
      request.get("/user/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
          email:this.email,
          address:this.address
        }
      }).then(res=>{
            console.log(res)
            this.tableData=res.data.records
            this.total=res.data.total
          }
      )
    },
    save(){//会话框
      request.post("/user",this.form).then(res=>{
        if(res.code === '200'){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else {
          this.$message.error("保存失败")
        }
      })
    },
    handleAdd(){//先打开然后再把值传进去
      this.dialogFormVisible=true
      this.form={}
    },
    handleEdit(row){//先传值再打开会话窗口
      this.form=row
      this.dialogFormVisible=true
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },
    del(id){//删除某一个
      request.delete("/user/"+id).then(res=>{
        if(res.code === '200'){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },
    delBatch(){
      let ids=this.multipleSelection.map(v=>v.id)// [{}, {}, {}] => [1,2,3] 把一个对象的数组转成一个纯id的数组
      request.post("/user/del/batch/",ids).then(res =>{
        if(res.code === '200'){
          this.$message.success("批量删除成功")
          this.load()
        }else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset(){
      this.email=""
      this.address=""
      this.username=""
      this.load()
    },
    collapse() {  // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {  // 收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {   // 展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    handleSizeChange(pageSize){
      console.log(pageSize);
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      console.log(pageNum)
      this.pageNum=pageNum
      this.load()
    },
    exp() {
      window.open("http://localhost:9090/user/export")
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    }

  }
}
</script>

<style scoped>

</style>