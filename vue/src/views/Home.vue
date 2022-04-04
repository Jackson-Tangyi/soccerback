<template>
  <div>
    <h1><span style="margin: 5px">Welcome to JUVENTUS management system</span></h1>
    <div style="padding-top: 10px">
      <el-row :gutter="10" style="margin-bottom: 60px">
        <el-col :span="12">
          <el-card style="color: #409EFF">
            <div id="compositionLine" style="width: 500px;height: 450px"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card style="color: #F56C6C">
            <div id="sexPie" style="width: 700px;height: 450px"></div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>

import request from "@/utils/request";
import * as echarts from "echarts";

export default {
  name: "Home",
  mounted() {

    var lineOption = {
      title: {
        text: 'club staff composition',
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
        data: ['Player','Coach','Doctor']
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
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(180, 180, 180, 0.2)'
          },
          label:{            //图形上的文本标签
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
        text: 'Gender ratio of all staff',
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

    var lineDom = document.getElementById('compositionLine');
    var lineChart = echarts.init(lineDom);

    var pieDom = document.getElementById('sexPie');
    var pieChart = echarts.init(pieDom);

    request.get("/echarts/whole/composition").then(res=>{
      lineOption.series[0].data=res.data
      lineOption.series[1].data=res.data

      lineChart.setOption(lineOption);
    })


    request.get("/echarts/whole/sex").then(res=>{

      pieOption.series[0].data=[
        {name:"Female" , value: res.data[0]},
        {name:"Male" , value: res.data[1]}
      ]
      pieChart.setOption(pieOption);
    })

  }

}



</script>

<style scoped>
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>