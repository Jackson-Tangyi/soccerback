<template>
  <div>
    <!--   搜索、重置 -->
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="Input name" suffix-icon="el-icon-search" v-model="name" ></el-input>
      <el-button class="ml-5" icon="el-icon-search" circle @click="load"></el-button>
      <el-button type="primary" @click="reset" round>Reset</el-button>
    </div>
    <!--   新增、批量删除 -->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">Add <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='Confirm'
          cancel-button-text='Rethink'
          icon="el-icon-info"
          icon-color="red"
          title="Confirm deletion？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">Batch deletion <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData" stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
      <el-table-column prop="name" label="Name" ></el-table-column>
      <el-table-column prop="season" label="Season" ></el-table-column>
      <el-table-column prop="image" label="Image">
        <template #default="scope">
          <el-image
              style="width: 50px; height: 50px"
              :src="scope.row.image"
              :preview-src-list="[scope.row.image]">
          </el-image>
        </template>
      </el-table-column>

      <el-table-column label="Operations"  width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">Edit <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='Confirm'
              cancel-button-text='Rethink'
              icon="el-icon-info"
              icon-color="red"
              title="Confirm deletion？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">Delete <i class="el-icon-remove-outline"></i></el-button>
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
    <el-dialog title="Achievement Information" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="100px" size="small">
        <el-form-item label="Name">
          <el-select v-model="form.name" placeholder="Select">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Season">
          <el-input v-model="form.season" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Image">
          <el-upload
              action="http://localhost:9090/file/upload"
              :show-file-list="false"
              style="display: inline-block"
              :on-success="filesUploadSuccess">
            <el-button type="primary">Upload</el-button>
          </el-upload>
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
  name: "Achievement",
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      options:[
        {
          value: 'Italian Cup Champion',
          label: 'Italian Cup Champion'
        }, {
          value: 'Italian Super Cup Champion',
          label: 'Italian Super Cup Champion'
        },{
          value: 'European Championship Cup Champion',
          label: 'European Championship Cup Champion'
        }, {
          value: 'Intercontinental Cup Champion',
          label: 'Intercontinental Cup Champion'
        }, {
          value: 'Club World Cup Champion',
          label: 'Club World Cup Champion'
        }, {
          value: 'Italian League Cup Champion',
          label: 'Italian League Cup Champion'
        }],
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get("/achievement/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name
        }
      }).then(res => {
            console.log(res)
            this.tableData = res.data.records
            this.total = res.data.total
          }
      )
    },
    save() {//会话框
      request.post("/achievement", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("Save successfully")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("Save failed")
        }
      })
    },
    handleAdd() {//先打开然后再把值传进去
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row) {//先传值再打开会话窗口
      this.form = row
      this.dialogFormVisible = true
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    del(id) {//删除某一个
      request.delete("/achievement/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("Delete successfully")
          this.load()
        } else {
          this.$message.error("Delete failed")
        }
      })
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)// [{}, {}, {}] => [1,2,3] 把一个对象的数组转成一个纯id的数组
      request.post("/achievement/del/batch/", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("Batch deletion successfully")
          this.load()
        } else {
          this.$message.error("Batch delete failed")
        }
      })
    },
    reset() {
      this.content = ""
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
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    filesUploadSuccess(res){//照片上传
      console.log(res)
      this.form.image=res
    }
  }
}
</script>

<style scoped>

</style>