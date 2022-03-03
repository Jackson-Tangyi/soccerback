<template>
<div>
  <div id="line" style="width: 500px;height: 400px"></div>
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
        text: '各月伤病数量统计',
        subtext: '趋势图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
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
    var lineDom = document.getElementById('line');
    var lineChart = echarts.init(lineDom);

    request.get("/echarts/treatment").then(res=>{
      console.log(res)
      lineOption.series[0].data=res.data
      lineOption.series[1].data=res.data

      lineChart.setOption(lineOption);
    })

  }
}
</script>

<style scoped>

</style>