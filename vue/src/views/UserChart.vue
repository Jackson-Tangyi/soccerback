<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 60px">
      <el-col :span="12">
        <el-card style="color: #409EFF">
          <div id="rose" style="width: 500px;height: 500px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card style="color: #F56C6C">
          <div id="pie" style="width: 500px;height: 500px;position: center"></div>
        </el-card>
      </el-col>
    </el-row>

  </div>

</template>


<script>
import * as echarts from 'echarts'
import request from "@/utils/request";

export default {
  name: "UserChart",
  data(){
    return{

    }
  },
  methods:{

  },
  mounted(){// 页面元素渲染之后再触发

    var roseOption = {
      title: {
        text: 'Registration Time',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        top: 'bottom'
      },
      toolbox: {
        show: true,
        feature: {
          mark: { show: true },
          dataView: { show: true, readOnly: true,width:100,height:100 },
          saveAsImage: { show: true }
        }
      },
      series: [
        {
          name: 'Registration Time',
          type: 'pie',
          radius: [40, 200],
          center: ['50%', '50%'],
          roseType: 'area',
          itemStyle: {
            borderRadius: 8
          },
          data: []
        }
      ]
    };


    var pieOption = {
      title: {
        text: 'Sex Ratio',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
      },
      legend: {
        orient: 'vertical',
        left: 'left',
      },
      series: [
        {
          name: 'Ratio',
          type: 'pie',
          radius: '50%',
          data:[],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };

    var roseDom = document.getElementById('rose');
    var roseChart = echarts.init(roseDom);

    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);

    request.get("/echarts/create/user").then(res=>{
      roseOption.series[0].data=[
          {value:res.data[0],name:"Season 1st"},
          {value:res.data[1],name:"Season 2nd"},
          {value:res.data[2],name:"Season 3rd"},
          {value:res.data[3],name:"Season 4th"}
      ]

      roseChart.setOption(roseOption)
    })

    request.get("/echarts/count/user").then(res=>{
      pieOption.series[0].data=[
        {name:"Female" , value: res.data[0]},
        {name:"Male" , value: res.data[1]}
      ]

      pieChart.setOption(pieOption)
    })


  },
  created() {

  }

}

</script>

<style scoped>

</style>