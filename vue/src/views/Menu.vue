<template>
<!-- 加不加div取决于整个页面 只能有一个根div 去包含所有 -->
<div>
  <div style="margin: 10px 0">
    <el-input style="width: 200px" placeholder="Input name" suffix-icon="el-icon-search" v-model="name" ></el-input>
    <el-button class="ml-5" icon="el-icon-search" circle @click="load"></el-button>
    <el-button type="primary" @click="reset" round>Reset</el-button>
  </div>

  <div style="margin: 10px 0">
    <el-button type="primary" @click="handleAdd(null)" round>Add <i class="el-icon-circle-plus-outline"></i></el-button>
    <el-popconfirm
        class="ml-5"
        confirm-button-text='Confirm'
        cancel-button-text='Rethink'
        icon="el-icon-info"
        icon-color="red"
        title="Confirm Deletion"
        @confirm="delBatch"
    >
      <el-button type="danger" slot="reference" round>Batch deletion <i class="el-icon-remove-outline"></i></el-button>
    </el-popconfirm>
  </div>

  <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
            row-key="id" default-expand-all @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="60"></el-table-column>
    <el-table-column prop="id" label="ID"  sortable></el-table-column>
    <el-table-column prop="name" label="Name" ></el-table-column>
    <el-table-column prop="path" label="Path" ></el-table-column>
    <el-table-column prop="pagePath" label="PagePath" ></el-table-column>
    <el-table-column label="Icon" class-name="fontSize18" align="center" label-class-name="fontSize12">
      <template slot-scope="scope">
        <span :class="scope.row.icon" />
      </template>
    </el-table-column>
    <el-table-column prop="description" label="Description" ></el-table-column>
    <el-table-column label="Operations"  width="300" align="center">
      <template slot-scope="scope">
        <el-button type="info" @click="handleAdd(scope.row.id)" v-if="!scope.row.pid && !scope.row.path" round>Add submenu <i class="el-icon-plus"></i></el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleEdit(scope.row)" circle></el-button>
        <el-popconfirm
            class="ml-5"
            confirm-button-text='Confirm'
            cancel-button-text='Rethink'
            icon="el-icon-info"
            icon-color="red"
            title="Confirm Deletion？"
            @confirm="del(scope.row.id)"
        >
          <el-button type="danger" icon="el-icon-delete" slot="reference" circle></el-button>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

  <!-- 会话框 -->
  <el-dialog title="Menu Information" :visible.sync="dialogFormVisible" width="30%" >
    <el-form label-width="100px" size="small">
      <el-form-item label="Name">
        <el-input v-model="form.name" autocomplete="off" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="Path">
        <el-input v-model="form.path" autocomplete="off" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="Page Path">
        <el-input v-model="form.pagePath" autocomplete="off" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="Icon">
        <el-select clearable v-model="form.icon" placeholder="Select" style="width: 100%">
          <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
            <i :class="item.value" /> {{ item.name }}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Description">
        <el-input type="textarea" v-model="form.description" autocomplete="off"></el-input>
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
  name: "Menu",
  data(){
    return{
      tableData: [],
      total: 0,
      name: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      options:[]
    }
  },
  created() {
    //请求分页数据
    this.load()
  },
  methods:{
    load(){
      request.get("/menu",{
        params:{
          name:this.name,
        }
      }).then(res=>{
            this.tableData=res.data
          }
      )

      request.get("/menu/icons").then(res=>{
        this.options=res.data
      })
    },
    save(){//会话框
      request.post("/menu",this.form).then(res=>{
        if(res.code === '200'){
          this.$message.success("Save successfully")
          this.dialogFormVisible=false
          this.load()
        }else {
          this.$message.error("Save failed")
        }
      })
    },
    handleAdd(pid){//先打开然后再把值传进去
      this.dialogFormVisible=true
      this.form={}
      if(pid){
        this.form.pid=pid
      }
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
      request.delete("/menu/"+id).then(res=>{
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
      request.post("/menu/del/batch/",ids).then(res =>{
        if(res.code === '200'){
          this.$message.success("Batch deletion successfully")
          this.load()
        }else {
          this.$message.error("Batch delete failed")
        }
      })
    },
    reset(){
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
    }
  }
}
</script>

<style scoped>

</style>