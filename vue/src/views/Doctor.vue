<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="Input name" suffix-icon="el-icon-search" v-model="name" ></el-input>
      <el-input style="width: 200px" placeholder="Input email" suffix-icon="el-icon-message" class="ml-5" v-model="email"></el-input>
      <el-input style="width: 200px" placeholder="Input address" suffix-icon="el-icon-position" class="ml-5" v-model="address"></el-input>
      <el-button class="ml-5" icon="el-icon-search" @click="load"></el-button>
      <el-button type="primary" @click="reset" round>Reset</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button v-if="user.role==='ROLE_ADMIN'" round type="primary" @click="handleAdd">Add <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='Confirm'
          cancel-button-text='Rethink'
          icon="el-icon-info"
          icon-color="red"
          title="Confirm deletion？"
          @confirm="delBatch"
      >
        <el-button v-if="user.role==='ROLE_ADMIN'" slot="reference" round type="danger">Batch delete <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload action="http://localhost:9090/doctor/import"
                 :show-file-list="false"
                 accept="xlsx"
                 :on-success="handleExcelImportSuccess"
                 style="display: inline-block">
        <el-button v-if="user.role==='ROLE_ADMIN'" class="ml-5" round type="success">Import <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button v-if="user.role==='ROLE_ADMIN'" class="ml-5" round type="info" @click="exp">Export <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column type="expand" label="Detail" width="80">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand" border="true">
            <el-form-item label=" email:">
              <span>{{ props.row.email }}</span>
            </el-form-item>
            <el-form-item label=" phone:">
              <span>{{ props.row.phone }}</span>
            </el-form-item>
            <el-form-item label=" Address:">
              <span>{{ props.row.address }}</span>
            </el-form-item>
            <el-form-item label=" Description:">
              <span>{{ props.row.description }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
      <el-table-column prop="name" label="Name"></el-table-column>
      <el-table-column prop="career" label="Career"></el-table-column>
      <el-table-column prop="age" label="Age"></el-table-column>
      <el-table-column prop="sex" label="Sex"></el-table-column>
      <el-table-column label="Cure records">
        <template slot-scope="scope">
          <el-button type="info" @click="handleShowTreatments(scope.row.treatments)" round>Cure records<i class="el-icon-folder-opened"></i></el-button>
        </template>
      </el-table-column>
      <el-table-column v-if="user.role==='ROLE_ADMIN'"  align="center" label="Operations">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" @click="handleEdit(scope.row)" circle></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='Confirm'
              cancel-button-text='Rethink'
              icon="el-icon-info"
              icon-color="red"
              title="Confirm deletion？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" icon="el-icon-delete" slot="reference" ></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--    分页插件    -->
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[10, 15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <!-- 编辑框 -->
    <el-dialog title="Doctor Information" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="100px" size="small">
        <el-form-item label="Name" >
          <el-input v-model="form.name" autocomplete="off" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="Age">
          <el-input-number v-model="form.age" :min="1" :max="120"></el-input-number>
        </el-form-item>
        <el-form-item label="Career">
          <el-select v-model="form.career" placeholder="Select">
            <el-option v-for="item in careers" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Sex">
          <el-select v-model="form.sex" placeholder="Select">
            <el-option v-for="item in sexs" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Phone">
          <el-input v-model="form.phone" autocomplete="off" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="Email">
          <el-input v-model="form.email" autocomplete="off" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="Address">
          <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Description">
          <el-input type="textarea" v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Save</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="treatmentVis">
      <el-table :data="treatments" border stripe>
        <el-table-column prop="description" label="Description"></el-table-column>
        <el-table-column prop="start" label="Start"></el-table-column>
        <el-table-column prop="end" label="End"></el-table-column>
        <el-table-column prop="player" label="Player"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Doctor",
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : "",
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      email: "",
      address: "",
      form: {},
      dialogFormVisible: false,
      treatmentVis:false,
      multipleSelection: [],
      treatments:[],
      sexs:[
        {
          value: 'Male',
          label: 'Male'
        }, {
          value: 'Female',
          label: 'Female'
        }],
      careers: [
        {
          value: 'Masseur ',//按摩师
          label: 'Masseur '
        }, {
          value: 'Orthopedist',//骨科医生
          label: 'Orthopedist'
        },{
          value: 'Nutritionist',//营养师
          label: 'Nutritionist'
        },{
          value: 'Sports psychologist',//运动心理医生
          label: 'Sports psychologist'
        },{
          value: 'Rehabilitation trainer',//康复训练师
          label: 'Rehabilitation trainer'
        }]
    }
  },
  created() {
    //请求分页数据
    this.load()
  },
  methods:{
    load(){
      request.get("/doctor/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
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
      request.post("/doctor",this.form).then(res=>{
        if(res.code === '200'){
          this.$message.success("Save successfully")
          this.dialogFormVisible=false
          this.load()
        }else {
          this.$message.error("Save failed")
        }
      })
    },
    handleAdd(){//先打开然后再把值传进去
      this.dialogFormVisible=true
      this.form={}
    },
    handleShowTreatments(treatments){
      this.treatments=treatments
      this.treatmentVis=true
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
      request.delete("/doctor/"+id).then(res=>{
        if(res.code === '200'){
          this.$message.success("Delete successfully")
          this.load()
        }else{
          this.$message.error("Delete failed")
        }
      })
    },
    delBatch(){
      let ids=this.multipleSelection.map(v=>v.id)// [{}, {}, {}] => [1,2,3] 把一个对象的数组转成一个纯id的数组
      request.post("/doctor/del/batch/",ids).then(res =>{
        if(res.code === '200'){
          this.$message.success("Batch deletion successfully")
          this.load()
        }else {
          this.$message.error("Batch delete failed")
        }
      })
    },
    reset(){
      this.email=""
      this.address=""
      this.name=""
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
      window.open("http://localhost:9090/doctor/export")
    },
    handleExcelImportSuccess() {
      this.$message.success("Import successfully")
      this.load()
    }

  }
}
</script>

<style scoped>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: rgb(48, 65, 86);
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 100%;
  font-weight: bold;
}
</style>