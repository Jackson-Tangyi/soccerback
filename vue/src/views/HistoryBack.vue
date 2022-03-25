<template>
  <div>
<!--   搜索、重置 -->
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="Input content" suffix-icon="el-icon-search" v-model="content" ></el-input>
      <el-button class="ml-5" type="primary" @click="load">Research</el-button>
      <el-button type="primary" @click="reset">Reset</el-button>
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

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
      <el-table-column prop="createTime" label="Create Time" width="140"></el-table-column>
      <el-table-column prop="icon" label="Icon" width="80"></el-table-column>
      <el-table-column prop="color" label="Color" width="80"></el-table-column>
      <el-table-column prop="content" label="Content"></el-table-column>
      <el-table-column prop="url" label="ImageURL">
        <template #default="scope">
          <el-image
              style="width: 50px; height: 50px"
              :src="scope.row.url"
              :preview-src-list="[scope.row.url]">
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
    <el-dialog title="History Information" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="100px" size="small">
        <el-form-item label="CreateTime">
          <template>
            <el-date-picker
                v-model="form.createTime"
                type="date"
                placeholder="Select Date"
                align="right"
            >
            </el-date-picker>
          </template>
        </el-form-item>
        <el-form-item label="Icon">
          <el-select clearable v-model="form.icon" placeholder="Please Select" style="width: 100%">
            <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
              <i :class="item.value" /> {{ item.name }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Color">
<!--      颜色拾取器    -->
          <el-input v-model="form.color" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Content">
          <el-input type="textarea" v-model="form.content" autocomplete="off"></el-input>
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
  name: "HistoryBack",
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      content: "",
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
  methods: {
    load() {
      request.get("/history/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          content: this.content
        }
      }).then(res => {
            console.log(res)
            this.tableData = res.data.records
            this.total = res.data.total
          }
      )

      request.get("/menu/icons").then(res=>{
        this.options=res.data
      })

    },
    save() {//会话框
      request.post("/history", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
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
      request.delete("/history/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)// [{}, {}, {}] => [1,2,3] 把一个对象的数组转成一个纯id的数组
      request.post("/history/del/batch/", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
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
    filesUploadSuccess(res){
      console.log(res)
      this.form.url=res
    }
  }
}
</script>

<style scoped>

</style>