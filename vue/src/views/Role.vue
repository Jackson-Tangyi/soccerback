<template>
<!-- 加不加div取决于整个页面 只能有一个根div 去包含所有 -->
<div>
  <div style="margin: 10px 0">
    <el-input style="width: 200px" placeholder="Input name" suffix-icon="el-icon-search" v-model="name" ></el-input>
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
  </div>

  <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="60"></el-table-column>
    <el-table-column prop="id" label="ID"  sortable></el-table-column>
    <el-table-column prop="name" label="Name" ></el-table-column>
    <el-table-column prop="description" label="Description" ></el-table-column>
    <el-table-column prop="flag" label="Flag" ></el-table-column>
    <el-table-column label="Operations"  width="300" align="center">
      <template slot-scope="scope">
        <el-button type="info" @click="selectMenu(scope.row)" round>Assign menu <i class="el-icon-menu"></i></el-button>
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

  <!-- Edit dialog -->
  <el-dialog title="Role Information" :visible.sync="dialogFormVisible" width="30%" >
    <el-form label-width="100px" size="small">
      <el-form-item label="Name">
        <el-input v-model="form.name" autocomplete="off" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="Flag">
        <el-input v-model="form.flag" autocomplete="off" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="Description">
        <el-input type="textarea" v-model="form.description" autocomplete="off" style="width: 300px"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">Cancel</el-button>
      <el-button type="primary" @click="save">Confirm</el-button>
    </div>
  </el-dialog>

<!--  Menu dialog -->
  <el-dialog title="Assign Menu" :visible.sync="menuDialogVis" width="30%">
    <el-tree
        :props="props"
        :data="menuData"
        show-checkbox
        node-key="id"
        ref="tree"
        :default-expanded-keys="expends"
        :default-checked-keys="checks">
         <span class="custom-tree-node" slot-scope="{ node, data }">
            <span><i :class="data.icon"></i> {{ data.name }}</span>
         </span>
    </el-tree>
    <div slot="footer" class="dialog-footer">
      <el-button @click="menuDialogVis = false">Cancel</el-button>
      <el-button type="primary" @click="saveRoleMenu">Confirm</el-button>
    </div>
  </el-dialog>


</div>



</template>

<script>
import request from "@/utils/request";

export default {
  name: "Role",
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      form: {},
      dialogFormVisible: false,
      menuDialogVis:false,
      roleId: 0,
      roleFlag:'',
      expends: [],
      props: {//使树形表给一个名字
        label: 'name',
      },
      checks: [],
      menuData:[],
      multipleSelection: [],
      ids: []
    }
  },
  created() {
    //请求分页数据
    this.load()
  },
  methods:{
    load(){
      request.get("/role/page",{//获取信息页面
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
        }
      }).then(res=>{
            console.log(res)
            this.tableData=res.data.records
            this.total=res.data.total
          }
      )

      this.request.get("/menu/ids").then(r => {//获取所有的菜单选项id
        this.ids = r.data
      })
    },
    save(){//会话框
      request.post("/role",this.form).then(res=>{
        if(res.code === '200'){
          this.$message.success("Save successfully")
          this.dialogFormVisible=false
          this.load()
        }else {
          this.$message.error("Save failed")
        }
      })
    },
    saveRoleMenu() {
      this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
        if (res.code === '200') {
          this.$message.success("Bind succeeded")
          this.menuDialogVis = false

          // 管理员角色修改页面后需要重新登录
          if (this.roleFlag === 'ROLE_ADMIN') {
            this.$store.commit("logout")
          }

        } else {
          this.$message.error(res.msg)
        }
      })
    },
    async selectMenu(role){
      this.roleId = role.id//获取当前行的角色id
      this.roleFlag = role.flag//获取当前行的角色唯一标识

      // 请求菜单数据
      this.request.get("/menu").then(res => {
        this.menuData = res.data

        // 把后台返回的菜单数据处理成 id数组
        this.expends = this.menuData.map(v => v.id)
      })

      this.request.get("/role/roleMenu/" + this.roleId).then(res => {
        this.checks = res.data
        this.ids.forEach(id => {
          if (!this.checks.includes(id)) {
            // 可能会报错：Uncaught (in promise) TypeError: Cannot read properties of undefined (reading 'setChecked')
            //也有另一种解决方式，见视频
            this.$nextTick(() => {
              this.$refs.tree.setChecked(id, false)
            })
          }
        })
        this.menuDialogVis = true
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
      request.delete("/role/"+id).then(res=>{
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
      request.post("/role/del/batch/",ids).then(res =>{
        if(res.code === '200'){
          this.$message.success("批量删除成功")
          this.load()
        }else {
          this.$message.error("批量删除失败")
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
.headerBg {
  background: #eee!important;
}
</style>