<template>
    <div>
      <div style="margin: 10px 0">
        <el-row :gutter="10">
          <el-col :span="8">
            <router-link to="/clue">
            <div class="card" style="display: flex; align-items: center">
              <div style="display: flex; flex-direction: column; gap:5px; margin-left: 40px;">
                <div style="color: #666">线索数量</div>
                <div style="font-size: 20px; text-align:center">{{ countData.clue }}</div>
              </div>
            </div>
          </router-link>
          </el-col>
          <el-col :span="8">
            <div class="card" style="display: flex; align-items: center" >
              <div style="display: flex; flex-direction: column; gap:5px; margin-left: 40px;">
                <div style="color: #666">已跟进记录数</div>
                <div style="font-size: 20px">{{ countData.potential }}</div>
              </div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="card" style="display: flex; align-items: center">
              <div style="display: flex; flex-direction: column; gap:5px; margin-left: 40px;">
                <div style="color: #666">跟进计划数</div>
                <div style="font-size: 20px">{{ countData.plan }}</div>
              </div>
            </div>
          </el-col>
          <el-col :span="8">

          </el-col>
        </el-row>
        
      </div>
  
      <el-row :gutter="10" style="margin: 10px 0">
    
      </el-row>
      <el-row :gutter="10" style="margin: 10px 0">
        <el-col :span="8">
          <div class="card" id="pie4" style="width: 100%; height: 400px"></div>
        </el-col>
        <el-col :span="8">
          <div class="card" id="pie6" style="width: 100%; height: 400px"></div>
        </el-col>
        <el-col :span="8">
          <div class="card" id="pie5" style="width: 100%; height: 400px"></div>
        </el-col>
      </el-row>

    </div>
  </template>
  
  <script>
  import * as echarts from 'echarts'
  
  //地区分布
  const barOption = {
    title: {
      text: '用户地区分布',
      left: 'center'
    },
    tooltip: {
      trigger: 'axis',
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: [],
        axisLabel: {
          interval: 0,  // Display all labels
          rotate: 45     // Rotate the labels for better readability
        },
        axisTick: {
          alignWithLabel: true
        }
      }
    ],
    yAxis: [
      {
        type: 'value',
        axisLabel: {
          formatter: function (value) { return parseInt(value); }
      }
      }
    ],
    series: [
      {
        name: 'Direct',
        type: 'bar',
        barWidth: '60%',
        data: []
      }
    ]
  };
  //跟进方式
  const pieOption4 = {
    title: {
      text: '跟进方式',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        type: 'pie',
        center: ['50%', '60%'],
        radius: '50%',
        data: [],
        label: {
          show: true,
          formatter(param) {
            return param.name + ' (' + param.percent + '%)';
          }
        },
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
  //跟进内容
  const pieOption5 = {
    title: {
      text: '跟进内容',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        type: 'pie',
        center: ['50%', '60%'],
        radius: '50%',
        data: [],
        label: {
          show: true,
          formatter(param) {
            return param.name + ' (' + param.percent + '%)';
          }
        },
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
  // 线索转换率
   const pieOption6 = {
     title: {
       text: '线索转换率',
       left: 'center'
     },
     tooltip: {
       trigger: 'item'
     },
     legend: {
       orient: 'vertical',
       left: 'left'
     },
     series: [
       {
         type: 'pie',
        center: ['50%', '60%'],
         radius: '50%',
         data: [],
         label: {
           show: true,
           formatter(param) {
             return param.name + ' (' + param.percent + '%)';
           }
         },
         emphasis: {
           itemStyle: {
             shadowBlur: 10,
             shadowOffsetX: 0,
             shadowColor: 'rgba(0, 0, 0, 0.5)'
           }
         }
       }
     ]
   }

  export default {
    name: 'Home',
    data() {
      return {
        user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
        notices: [],
        countData: {}
      }
    },
    mounted() {
      this.$request.get('/countData').then(res => {
        this.countData = res.data
      })

      // 跟进方式
      let pieDom4 = document.getElementById('pie4');
      let pieChart4 = echarts.init(pieDom4);
      //跟进内容
      let pieDom5 = document.getElementById('pie5');
      let pieChart5 = echarts.init(pieDom5);
     
      //线索转换率
       let pieDom6 = document.getElementById('pie6');
       let pieChart6 = echarts.init(pieDom6);
      //跟进方式
      this.$request.get('/MethodData').then(res => {
        pieOption4.series[0].data = res.data || []
        pieChart4.setOption(pieOption4)
      })
      //跟进内容
      this.$request.get('/ContentData').then(res => {
        pieOption5.series[0].data = res.data || []
        pieChart5.setOption(pieOption5)
      })
         //线索转换率
        this.$request.get('/FallbackData').then(res => {
         pieOption6.series[0].data = res.data || []
         pieChart6.setOption(pieOption6)
       })
    }
  }
  </script>
  <style scoped>
  .head {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    display: block;
    margin: 0 auto;
  }
  
  .userinfo .name {
    font-size: 32px;
    margin-bottom: 10px;
    text-align: center;
  }
  
  .info {
    font-size: 15px;
    line-height: 30px;
    text-align: center;
  }
  
  </style>
  
  