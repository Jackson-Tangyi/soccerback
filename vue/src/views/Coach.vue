<template>
  <div>
<!--  搜索窗口  -->
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="Input Name" suffix-icon="el-icon-search" v-model="name" ></el-input>
      <el-input style="width: 200px" placeholder="Input email" suffix-icon="el-icon-message" class="ml-5" v-model="email"></el-input>
      <el-input style="width: 200px" placeholder="Input address" suffix-icon="el-icon-position" class="ml-5" v-model="address"></el-input>
      <el-button class="ml-5" icon="el-icon-search" circle @click="load"></el-button>
      <el-button type="primary" @click="reset" round>Reset</el-button>
    </div>
<!-- 新增、批量删除、导入、导出 -->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd" round>Add <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-upload action="http://localhost:9090/coach/import"
                 :show-file-list="false"
                 accept="xlsx"
                 :on-success="handleExcelImportSuccess"
                 style="display: inline-block">
        <el-button type="success" class="ml-5" round>Import <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="info" @click="exp" class="ml-5" round>Export <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column type="expand" label="Detail" width="80">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand" border="true">
            <el-form-item label=" Name:">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <el-form-item label=" Email :">
              <span>{{ props.row.email }}</span>
            </el-form-item>
            <el-form-item label=" Phone :">
              <span>{{ props.row.phone }}</span>
            </el-form-item>
            <el-form-item label=" Address :">
              <span>{{ props.row.address}}</span>
            </el-form-item>
            <el-form-item label=" Description :">
              <span>{{ props.row.description }}</span>
            </el-form-item>

          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
      <el-table-column prop="name" label="Name" ></el-table-column>
      <el-table-column prop="career" label="Career" ></el-table-column>
      <el-table-column prop="sex" label="Sex" ></el-table-column>
      <el-table-column prop="cid" label="CoachID"></el-table-column>
      <el-table-column label="Image" >
        <template #default="scope">
          <el-image
              style="width: 50px; height: 50px"
              :src="scope.row.image"
              :preview-src-list="[scope.row.image]">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="Operations"  width="400" align="center">
        <template slot-scope="scope">
          <el-button type="warning" @click="lookGames(scope.row.games)" round>Games attended <i class="el-icon-document"></i></el-button>
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
            <el-button type="danger" icon="el-icon-delete" slot="reference" circle></el-button>
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
          :page-sizes="[5, 10, 15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <!-- 编辑框 -->
    <el-dialog title="Coach Information" :visible.sync="dialogFormVisible" width="50%" >
      <el-form label-width="100px" size="small">
        <el-row>
          <el-col :span="12">
            <el-form-item label="CoachID">
            <el-input v-model="form.cid" autocomplete="off" :disabled="form.cid?true:false"></el-input>
          </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Name">
              <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="Age">
              <el-input-number v-model="form.age" :min="1" :max="120"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Sex">
              <el-select v-model="form.sex" placeholder="Select">
                <el-option v-for="item in sexs" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="Phone">
              <el-input v-model="form.phone" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Email">
              <el-input v-model="form.email" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Address">
              <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Description">
              <el-input type="textarea" v-model="form.description" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Player Image" label-width="120px">
              <el-upload
                  action="http://localhost:9090/file/upload"
                  :show-file-list="false"
                  style="display: inline-block"
                  :on-success="filesUploadSuccess">
                <el-button type="primary">Upload</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Career">
              <el-select v-model="form.career" placeholder="Select">
                <el-option v-for="item in careers" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Save</el-button>
      </div>
    </el-dialog>

<!--  出席的比赛 -->
    <el-dialog title="Game Attended" :visible.sync="vis" width="50%" >
      <el-table :data="games" border stripe>
        <el-table-column prop="name" label="Name" width="120"></el-table-column>
        <el-table-column prop="date" label="date"></el-table-column>
        <el-table-column prop="homeaway" label="Home/Away"></el-table-column>
        <el-table-column prop="score" label="Score"></el-table-column>
        <el-table-column prop="place" label="Place"></el-table-column>
        <el-table-column prop="type" label="Type"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Coach",
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      email: "",
      address: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      vis:false,
      games:[],
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
          value: 'Head Coach ',//主教练
          label: 'Head Coach '
        }, {
          value: 'Assist Coach',//助理教练
          label: 'Assist Coach'
        },{
          value: 'Physical Coach',//体能教练
          label: 'Physical Coach'
        },{
          value: 'Tactics Coach',//战术教练
          label: 'Tactics Coach'
        },{
          value: 'Goalkeeper Coach',//守门员教练
          label: 'GK Coach'
        }]
    }
  },
  created() {
    //请求分页数据
    this.load()
  },
  methods:{
    lookGames(games){
      this.games=games
      this.vis=true
    },
    load(){
      request.get("/coach/page",{
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
      request.post("/coach",this.form).then(res=>{
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
    handleEdit(row){//先传值再打开会话窗口
      this.form=row
      this.dialogFormVisible=true
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },
    del(id){//删除某一个
      request.delete("/coach/"+id).then(res=>{
        if(res.code === '200'){
          this.$message.success("Delete successfully")
          this.load()
        }else{
          this.$message.error("Delete failed")
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
      window.open("http://localhost:9090/coach/export")
    },
    handleExcelImportSuccess() {
      this.$message.success("Import successfully")
      this.load()
    },
    filesUploadSuccess(res){
    console.log(res)
    this.form.image=res
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