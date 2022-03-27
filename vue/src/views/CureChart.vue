<template>
<div>
  <div>
    <el-row :gutter="10" style="margin-bottom: 60px">
      <el-col :span="12">
        <el-card style="color: #409EFF">
          <div id="line" style="width: 700px;height: 500px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card style="color: #F56C6C">
          <div id="pie" style="width: 700px;height: 500px;position: center"></div>
        </el-card>
      </el-col>
    </el-row>

  </div>
</div>
</template>

<script>
import * as echarts from 'echarts';
import request from "@/utils/request";

export default {
  name: "Chart",
  data(){
    return{

    }
  },
  mounted() {

    var lineOption = {
      title: {
        text: 'Number of Injuries by Month',
        subtext: '  ',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul','Aug','Sep','Oct','Nov','Dec']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line',
        },
        {
          data: [],
          type: 'bar',
          label:{            //饼图图形上的文本标签
            normal:{
              show:true,
              position:'inside', //标签的位置
              textStyle : {
                fontWeight : 300 ,
                fontSize : 14,    //文字的字体大小
                color: "#FFF"
              },
            }
          },
        }
      ]
    }

    var pieOption = {
      title: {
        text: 'Injury Type Statistics',
        subtext: '  ',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        top: '5%',
        left: 'center'
      },
      series: [
        {
          name: 'Injury Type',
          type: 'pie',
          radius: ['40%', '60%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: {
            show: false,
            position: 'center'
          },
          emphasis: {
            label: {
              show: true,
              fontSize: '40',
              fontWeight: 'bold'
            }
          },
          labelLine: {
            show: false
          },
          data: []
        }
      ]
    };

    var lineDom = document.getElementById('line');
    var lineChart = echarts.init(lineDom);

    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);

    request.get("/echarts/treatment/month").then(res=>{
      lineOption.series[0].data=res.data
      lineOption.series[1].data=res.data

      lineChart.setOption(lineOption);
    })


    request.get("/echarts/treatment/type").then(res=>{
      console.log(res)


      pieOption.series[0].data=[
        {value: res.data[0],name:'Feet'},
        {value: res.data[1],name:'Thigh'},
        {value: res.data[2],name:'Abdomen'},
        {value: res.data[3],name:'Arm'},
        {value: res.data[4],name:'Back'},
        {value: res.data[5],name:'Head'}
      ]
      pieChart.setOption(pieOption);
    })




  }
}
</script>

<style scoped>

</style>