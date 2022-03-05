<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 60px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid" /> 用户总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-money" /> 销售总量</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            ￥ 1000000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-bank-card" /> 收益总额</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            ￥ 300000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-s-shop" /> 门店总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            20
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-button @click="check" type="primary">check diagram</el-button>

    <el-dialog :visible="vis" width="50%">
      <el-card id="main" style="width: 300px; height: 250px"></el-card>
<!--      <div id="main" style="width: 300px; height: 250px"></div>-->
      <div slot="footer" class="dialog-footer">
        <el-button @click="vis = false">Close</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import * as echarts from 'echarts'
export default {
  name: "Home",
  data(){
    return{
      vis:false
    }
  },
  methods:{
    check(){
      this.vis=true
      setTimeout(()=>{
        this.draw()
      },300)
    },
    draw(){
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [150, 230, 224, 218, 135, 147, 260],
            type: 'line'
          }
        ]
      };

      option && myChart.setOption(option);
    }
  },
  mounted(){// 页面元素渲染之后再触发

    // // 饼图
    // var pieOption = {
    //   title: {
    //     text: '各季度会员数量统计',
    //     subtext: '比例图',
    //     left: 'center'
    //   },
    //   tooltip: {
    //     trigger: 'item',
    //     formatter: '{a} <br/>{b} : {c} ({d}%)'
    //   },
    //   legend: {
    //     orient: 'vertical',
    //     left: 'left'
    //   },
    //   series: [
    //     {
    //       name: "星巴克",
    //       type: 'pie',
    //       radius: '55%',
    //       center: ['25%', '70%'],
    //       label:{            //饼图图形上的文本标签
    //         normal:{
    //           show:true,
    //           position:'inner', //标签的位置
    //           textStyle : {
    //             fontWeight : 300 ,
    //             fontSize : 14,    //文字的字体大小
    //             color: "#fff"
    //           },
    //           formatter:'{c}({d}%)'
    //         }
    //       },
    //       data: [],  // 填空
    //       emphasis: {
    //         itemStyle: {
    //           shadowBlur: 10,
    //           shadowOffsetX: 0,
    //           shadowColor: 'rgba(0, 0, 0, 0.5)'
    //         }
    //       }
    //     },
    //     {
    //       name: "瑞幸咖啡",
    //       type: 'pie',
    //       radius: '50%',
    //       center: ['75%', '50%'],
    //       label:{            //饼图图形上的文本标签
    //         normal:{
    //           show:true,
    //           position:'inner', //标签的位置
    //           textStyle : {
    //             fontWeight : 300 ,
    //             fontSize : 14,    //文字的字体大小
    //             color: "#fff"
    //           },
    //           formatter:'{d}%'
    //         }
    //       },
    //       data: [
    //         {name: "第一季度", value: 5},
    //         {name: "第二季度", value: 6},
    //         {name: "第三季度", value: 7},
    //         {name: "第四季度", value: 8}
    //       ],  // 填空
    //       emphasis: {
    //         itemStyle: {
    //           shadowBlur: 10,
    //           shadowOffsetX: 0,
    //           shadowColor: 'rgba(0, 0, 0, 0.5)'
    //         }
    //       }
    //     }
    //   ],
    //
    // };
    //
    //
    // var chartDom = document.getElementById('main');
    // var myChart = echarts.init(chartDom);
    //
    // var pieDom = document.getElementById('pie');
    // var pieChart = echarts.init(pieDom);
    //
    // this.request.get("/echarts/members").then(res => {
    //   // 填空
    //   // option.xAxis.data = res.data.x
    //   console.log(res)
    //   option.series[0].data = [5,6,7,8]
    //   option.series[1].data = [5,6,7,8]
    //
    //   option.series[2].data = [5,6,7,8]
    //   option.series[3].data = [5,6,7,8]
    //   // 数据准备完毕之后再set
    //   myChart.setOption(option);
    //
    //
    //   pieOption.series[0].data = [
    //     {name: "第一季度", value: res.data[0]},
    //     {name: "第二季度", value: res.data[1]},
    //     {name: "第三季度", value: res.data[2]},
    //     {name: "第四季度", value: res.data[3]},
    //   ]
    //   pieChart.setOption(pieOption)
    // })
  }

}



</script>

<style scoped>

</style>