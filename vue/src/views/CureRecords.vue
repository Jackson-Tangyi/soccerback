<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="Input name" suffix-icon="el-icon-search" v-model="description" ></el-input>
      <el-button class="ml-5" icon="el-icon-search" circle @click="load"></el-button>
      <el-button type="primary" @click="reset" round>Reset</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd" round>Add <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='Confirm'
          cancel-button-text='Rethink'
          icon="el-icon-info"
          icon-color="red"
          title="Confirm deletion？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference" round>Batch deletion <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload action="http://localhost:9090/cure/import"
                 :show-file-list="false"
                 accept="xlsx"
                 :on-success="handleExcelImportSuccess"
                 style="display: inline-block">
        <el-button type="success" class="ml-5" round>Import <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="info" @click="exp" class="ml-5" round>Export <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
      <el-table-column prop="player" label="Player" width="180"></el-table-column>
      <el-table-column prop="doctor" label="Doctor" width="200"></el-table-column>
      <el-table-column prop="start" label="Start" width="150"></el-table-column>
      <el-table-column prop="end" label="End" width="150"></el-table-column>
      <el-table-column prop="place" label="Place" width="150"></el-table-column>
      <el-table-column prop="description" label="Description"></el-table-column>
      <el-table-column label="Operations"  width="200" align="center">
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

    <!-- 会话框 -->
    <el-dialog title="Cure Information" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="100px" size="small">
        <el-form-item label="Player">
          <el-select v-model="form.pnum" placeholder="Select">
            <el-option v-for="item in players" :key="item.number" :label="item.name" :value="item.number"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Doctor">
          <el-select v-model="form.did" placeholder="Select">
            <el-option v-for="item in doctors" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Start">
          <template>
            <el-date-picker
                v-model="form.start"
                type="date"
                placeholder="Select Date"
                align="right"
            >
            </el-date-picker>
          </template>
        </el-form-item>
        <el-form-item label="End">
          <el-input type="textarea" v-model="form.end" autocomplete="off" style="width: 300px"></el-input>
        </el-form-item>
        <el-form-item label="Place">
          <el-select v-model="form.place" placeholder="Select">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
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

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Cure",
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      description: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      players:[],
      doctors:[],
      options: [{
        value: 'Feet',
        label: 'Feet'
      },{
        value: 'Thigh',
        label: 'Thigh'
      },{
        value: 'Abdomen',
        label: 'Abdomen'
      },{
        value: 'Arm',
        label: 'Arm'
      },{
        value: 'Back',
        label: 'Back'
      },{
        value: 'Head',
        label: 'Head'
      }]
    }
  },
  created() {
    //请求分页数据
    this.load()
  },
  methods:{
    load(){
      request.get("/cure/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          description:this.description,
        }
      }).then(res=>{
            console.log(res)
            this.tableData=res.data.records
            this.total=res.data.total
          }
      )

      request.get("/player/findPlayers").then(res=>{
        this.players=res.data
      })

      request.get("/doctor/findDoctors").then(res=>{
        this.doctors=res.data
      })
    },
    save(){//会话框
      request.post("/cure",this.form).then(res=>{
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
      request.delete("/cure/"+id).then(res=>{
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
      request.post("/cure/del/batch/",ids).then(res =>{
        if(res.code === '200'){
          this.$message.success("Batch deletion successfully")
          this.load()
        }else {
          this.$message.error("Batch delete failed")
        }
      })
    },
    reset(){
      this.description=""
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
      window.open("http://localhost:9090/cure/export")
    },
    handleExcelImportSuccess() {
      this.$message.success("Import successfully")
      this.load()
    }

  }
}
</script>

<style scoped>

</style>