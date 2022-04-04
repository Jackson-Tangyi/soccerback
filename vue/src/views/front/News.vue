<template>
  <div style="color: #666">
    <div style="margin: 10px 0">
      <el-input v-model="name" placeholder="Input name" size="small" style="width: 300px" suffix-icon="el-icon-search"></el-input>
      <el-button circle class="ml-5" icon="el-icon-search" type="warning" @click="load"></el-button>
      <el-button round type="primary" @click="reset">Reset</el-button>
    </div>

    <div style="margin: 10px 0">
      <div v-for="item in tableData" :key="item.id" style="padding: 10px 0; border-bottom: 1px dashed #ccc">
        <div class="pd-10" style="font-size: 20px; color: #3F5EFB; cursor: pointer" @click="$router.push('/front/frontNewsDetail?id=' + item.id)">{{ item.name }}</div>
        <div style="font-size: 14px; margin-top: 10px">
          <i class="el-icon-user-solid"></i> <span>{{ item.user }}</span>
          <i class="el-icon-time" style="margin-left: 10px"></i> <span>{{ item.time }}</span>
        </div>
      </div>
    </div>


    <div style="padding: 10px 0">
      <el-pagination
          :current-page="pageNum"
          :page-size="pageSize"
          :page-sizes="[2, 5, 10, 20]"
          :total="total"
          layout="total, prev, pager, next"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "News",
  data() {
    return {
      form: {},
      tableData: [],
      name: '',
      multipleSelection: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      dialogFormVisible: false,
      teachers: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      content: '',
      viewDialogVis: false
    }
  },
  created() {
    this.load()
  },
  methods: {
    view(content) {
      this.content = content
      this.viewDialogVis = true
    },
    load() {
      this.request.get("/news/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {

        this.tableData = res.data.records
        this.total = res.data.total

      })

    },
    changeEnable(row) {
      this.request.post("/news/update", row).then(res => {
        if (res.code === '200') {
          this.$message.success("Successful operation")
        }
      })
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    reset() {
      this.name = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    }
  }
}
</script>

<style scoped>

</style>