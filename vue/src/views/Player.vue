<template xmlns:el-col="http://www.w3.org/1999/html">
  <!-- 加不加div取决于整个页面 只能有一个根div 去包含所有 -->
  <div>
<!--  搜索窗口  -->
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="Input name" suffix-icon="el-icon-search" v-model="name" ></el-input>
      <el-button class="ml-5" icon="el-icon-search" circle @click="load"></el-button>
      <el-button type="primary" @click="reset" round>Reset</el-button>
    </div>
<!-- 新增、批量删除、导入、导出 -->
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
        <el-button type="danger" slot="reference" round>Batch delete <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload action="http://localhost:9090/player/import"
                 :show-file-list="false"
                 accept="xlsx"
                 :on-success="handleExcelImportSuccess"
                 style="display: inline-block">
        <el-button type="success" class="ml-5" round>Import <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="info" @click="exp" class="ml-5" round>Export <i class="el-icon-top"></i></el-button>
    </div>

<!-- 数据展示表格   -->
    <el-table :data="tableData" stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column type="expand" label="Detail" width="80">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand" border="true">
            <el-form-item label=" Name:">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <el-form-item label=" Number :">
              <span>{{ props.row.number}}</span>
            </el-form-item>
            <el-form-item label=" Weight :">
              <span>{{ props.row.weight }} kg</span>
            </el-form-item>
            <el-form-item label=" Height :">
              <span>{{ props.row.height }} cm</span>
            </el-form-item>
            <el-form-item label=" Country :">
              <span>{{ props.row.country }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
      <el-table-column prop="name" label="Name" ></el-table-column>
      <el-table-column prop="area" label="Area" width="100"></el-table-column>
      <el-table-column label="Image" >
        <template #default="scope">
          <el-image
              style="width: 50px; height: 50px"
              :src="scope.row.image"
              :preview-src-list="[scope.row.image]">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="Ability" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleShow(scope.row.id)" round>Show<i class="el-icon-data-analysis"></i></el-button>
        </template>
      </el-table-column>
      <el-table-column label="Cure records" align="center">
        <template slot-scope="scope">
          <el-button type="info" @click="handleShowTreatments(scope.row.treatments)" round>Cure records<i class="el-icon-folder-opened"></i></el-button>
        </template>
      </el-table-column>
      <el-table-column label="Operations" align="center">
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

    <!-- 编辑窗口 -->
    <el-dialog title="Player Information" :visible.sync="dialogFormVisible" width="50%" v-el-drag-dialog>
      <el-form label-width="80px" size="small">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Number" label-width="120px">
              <el-input-number v-model="form.number" :min="1" :max="99" :disabled="form.number?true:false"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Age" label-width="120px">
              <el-input-number v-model="form.age" :min="1" :max="120"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Name" label-width="120px">
              <el-input prefix-icon="el-icon-edit" v-model="form.name" autocomplete="off" style="width: 200px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Country" label-width="120px">
              <el-input prefix-icon="el-icon-s-flag" v-model="form.country" autocomplete="off" style="width: 200px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="Height" label-width="120px">
              <el-input-number v-model="form.height" :min="1" :max="200"></el-input-number> cm
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Weight" label-width="120px">
              <el-input-number v-model="form.weight" :min="1" :max="100"></el-input-number> kg
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="Speed" label-width="120px">
              <el-input-number v-model="form.speed" :min="1" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Pass" label-width="120px">
              <el-input-number v-model="form.pass" :min="1" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="Defence" label-width="120px">
              <el-input-number v-model="form.defence" :min="1" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Power" label-width="120px">
              <el-input-number v-model="form.power" :min="1" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="Shot" label-width="120px">
              <el-input-number v-model="form.shot" :min="1" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Dribbling" label-width="120px">
              <el-input-number v-model="form.dribbling" :min="1" :max="100"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="Area" label-width="120px">
            <el-select v-model="form.area" placeholder="Select">
              <el-option-group
                  v-for="area in options"
                  :key="area.label"
                  :label="area.label">
                <el-option
                    v-for="item in area.options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-option-group>
            </el-select>
          </el-form-item>
        </el-row>

        <el-row>
          <el-form-item label="Player Image" label-width="120px">
            <el-upload
                action="http://localhost:9090/file/upload"
                :show-file-list="false"
                style="display: inline-block"
                :on-success="filesUploadSuccess">
              <el-button type="primary">Upload</el-button>
            </el-upload>
          </el-form-item>
        </el-row>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Save</el-button>
      </div>
    </el-dialog>

<!-- 治疗记录窗口 -->
    <el-dialog :visible.sync="treatmentVis" v-el-drag-dialog>
      <el-table :data="treatments" border stripe>
        <el-table-column prop="place" label="Place" width="120"></el-table-column>
        <el-table-column prop="description" label="Description" width="120"></el-table-column>
        <el-table-column prop="start" label="Start"></el-table-column>
        <el-table-column prop="end" label="End"></el-table-column>
        <el-table-column prop="doctor" label="Doctor"></el-table-column>
      </el-table>
    </el-dialog>

<!-- 球员能力值雷达图展示 -->
    <el-dialog :visible.sync="radarVisible" v-el-drag-dialog>
      <div id="main" style="width: 400px; height: 400px"></div>
    </el-dialog>

  </div>
</template>

<script>
import request from "@/utils/request";
import * as echarts from 'echarts';
import elDragDialog from "@/directive/el-dragDialog";


export default {
  name: "Player",
  directives:{
    elDragDialog
  },
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      form: {},
      dialogFormVisible: false,
      radarVisible:false,
      treatmentVis:false,
      multipleSelection: [],
      treatments:[],
      options: [{
        label: 'Front Court',
        options: [{
          value: 'Striker ',//中锋
          label: 'ST '
        }, {
          value: 'Center Forward',//影子前锋
          label: 'CF'
        },{
          value: 'Left Wing',//左边锋
          label: 'LW'
        },{
          value: 'Right Wing',//右边锋
          label: 'RW'
        }]
      }, {
        label: 'Midfielder ',
        options: [{
          value: 'Center Midfielder',//中场
          label: 'CM'
        }, {
          value: 'Center Defensive Midfielder',//后腰
          label: 'CDM'
        }, {
          value: 'Center Attacking Midfielder',//前腰
          label: 'CAM'
        }, {
          value: 'Left Midfielder',//左中场
          label: 'LM'
        }, {
          value: 'Right Midfielder',//右中场
          label: 'RM'
        }]
      },{
        label: 'Defender ',
        options: [{
          value: 'Sweeper',//清道夫
          label: 'SW'
        }, {
          value: 'Center-back',//中后卫
          label: 'CB'
        }, {
          value: 'Left-back',//左边后卫
          label: 'LB'
        }, {
          value: 'Right-back',//右边后卫
          label: 'RB'
        }]
      },{
        label: 'Goal Keeper',
        options: [{
          value: 'Goal Keeper',//门将
          label: 'GK'
        }]
      }]
    }
  },
  created() {
    //请求分页数据
    this.load()
  },methods:{
    displayRadar(val){
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: 'Ability Display'
        },
        legend: {
          bottom:5,
          data: ['Allocated Budget']
        },
        radar: {
          axisName:{
            color:'rgb(255,0,0)'
          },
          indicator: [
            { name: 'Speed', max: 100 },
            { name: 'Power', max: 100 },
            { name: 'defence', max: 100 },
            { name: 'dribbling', max: 100 },
            { name: 'pass', max: 100 },
            { name: 'shot', max: 100 }
          ],
          splitArea: {
            show: false
          },
          axisLine: {
            lineStyle: {
              color: 'rgba(238, 197, 102, 1.5)'
            }
          }
        },
        series: [
          {
            name: 'Budget vs spending',
            type: 'radar',
            data: [
              {
                value: [],
                name: 'Allocated Budget'
              }
            ],
            label:{
              normal:{
                show:true
              }
            }
          }
        ]
      };

      request.get("/echarts/radar/ "+val).then(res=>{
        option.series[0].data[0]=res.data
        option && myChart.setOption(option);
      })

    },
    load(){
      request.get("/player/page",{
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
    },
    save(){//会话框
      request.post("/player",this.form).then(res=>{
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
    handleShow(val){
      this.radarVisible=true
      setTimeout(()=>{
        this.displayRadar(val)
      },300)
    },
    handleShowTreatments(treatments){
      this.treatments=treatments
      this.treatmentVis=true
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },
    del(id){//删除某一个
      request.delete("/player/"+id).then(res=>{
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
      request.post("/player/del/batch/",ids).then(res =>{
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
    },
    exp() {
      window.open("http://localhost:9090/player/export")
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