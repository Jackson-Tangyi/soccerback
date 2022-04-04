<template>
  <div>
    <div style="margin: 10px 0">
      <el-input v-model="name" placeholder="Input name" style="width: 200px" suffix-icon="el-icon-search"></el-input>
      <el-button circle class="ml-5" icon="el-icon-search" type="warning" @click="load"></el-button>
      <el-button round type="primary" @click="reset">Reset</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button v-if="user.role === 'ROLE_ADMIN'" type="primary" @click="handleAdd">Add <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          cancel-button-text='Rethink'
          class="ml-5"
          confirm-button-text='Confirm'
          icon="el-icon-info"
          icon-color="red"
          title="Confirm deletion？"
          @confirm="delBatch"
      >
        <el-button slot="reference" round type="danger">Batch deletion <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

    </div>
    <el-table :data="tableData" :header-cell-class-name="'headerBg'" border stripe
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="ID" prop="id" width="80"></el-table-column>
      <el-table-column label="Title" prop="name"></el-table-column>
      <el-table-column label="Content" prop="content">
        <template slot-scope="scope">
          <el-button type="primary" @click="view(scope.row.content)">Preview</el-button>
        </template>
      </el-table-column>
      <el-table-column label="publisher" prop="user"></el-table-column>
      <el-table-column label="release time" prop="time"></el-table-column>
      <el-table-column align="center" label="Operation" width="280">
        <template slot-scope="scope">
          <el-button circle icon="el-icon-edit" type="primary" @click="handleEdit(scope.row)"></el-button>
          <el-popconfirm
              cancel-button-text='Rethink'
              class="ml-5"
              confirm-button-text='Confirm'
              icon="el-icon-info"
              icon-color="red"
              title="Confirm deletion？"
              @confirm="del(scope.row.id)"
          >
            <el-button slot="reference" circle icon="el-icon-delete" type="danger"></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          :current-page="pageNum"
          :page-size="pageSize"
          :page-sizes="[2, 5, 10, 20]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
      </el-pagination>
    </div>

    <el-dialog :visible.sync="dialogFormVisible" title="News Information" width="60%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="Title">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Content">
          <mavon-editor ref="md"
                        v-model="form.content"
                        :ishljs="true"
                        language="en"
                        @imgAdd="imgAdd"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Save</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="viewDialogVis" title="News Information" width="60%" >
        <div>
          <mavon-editor
              ref="md"
              :defaultOpen="'preview'"
              :editable="false"
              :ishljs="true"
              :scrollStyle="true"
              :subfield="false"
              :toolbarsFlag="false"
              :value="content"
              class="md"
              language="'en'"
          />
        </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "News",
  data() {
    return {
      form: {},
      tableData: [],
      name: '',
      multipleSelection: [],
      pageNum: 1,
      pageSize: 10,
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
    // 绑定@imgAdd event
    imgAdd(pos, $file) {
      let $vm = this.$refs.md
      // 第一步.将图片上传到服务器.
      const formData = new FormData();
      formData.append('file', $file);
      axios({
        url: 'http://localhost:9090/file/upload',
        method: 'post',
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'},
      }).then((res) => {
        // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
        $vm.$img2Url(pos, res.data);
      })
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
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    del(id) {
      this.request.delete("/news/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("Delete successfully")
          this.load()
        } else {
          this.$message.error("Delete failed")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/news/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("Batch deletion successfully")
          this.load()
        } else {
          this.$message.error("Batch delete failed")
        }
      })
    },
    save() {
      this.request.post("/news", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("Save successfully")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("Save failed")
        }
      })
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
    },
    download(url) {
      window.open(url)
    },
  }
}
</script>

<style scoped>

</style>