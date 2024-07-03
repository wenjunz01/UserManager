<template>
  <div>
    <!-- <div class="card" style="padding: 15px">
      您好，{{ user?.name }}！欢迎使用本系统
    </div> -->

    <div style="margin: 10px 0">
      <el-row :gutter="10">
        <el-col :span="4">
          <router-link to="/customer">
          <div class="card" style="display: flex; align-items: center">
            <div style="display: flex; flex-direction: column; gap:5px; margin-left: 40px;">
              <div style="color: #666">用户总数</div>
              <div style="font-size: 20px; text-align:center">{{ countData.user }}</div>
            </div>
          </div>
        </router-link>
        </el-col>
        <el-col :span="4">
          <div class="card" style="display: flex; align-items: center" >
            <div style="display: flex; flex-direction: column; gap:5px; margin-left: 40px;">
              <div style="color: #666">公海用户数</div>
              <div style="font-size: 20px">{{ countData.publicuser }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="card" style="display: flex; align-items: center">
            <div style="display: flex; flex-direction: column; gap:5px; margin-left: 40px;">
              <div style="color: #666">第三方用户数</div>
              <div style="font-size: 20px">{{ countData.tripartite }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="card" style="display: flex; align-items: center">
            <div style="display: flex; flex-direction: column; gap:5px; margin-left: 40px;">
              <div style="color: #666">合同数量</div>
              <div style="font-size: 20px">{{ countData.contract }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="card" style="display: flex; align-items: center">
            <div style="display: flex; flex-direction: column; gap:5px; margin-left: 40px;">
              <div style="color: #666">订单数量</div>
              <div style="font-size: 20px">{{ countData.saledate }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="card" style="display: flex; align-items: center">
            <div style="display: flex; flex-direction: column; gap:5px; margin-left: 40px;">
              <div style="color: #666">产品金额总数</div>
              <div style="font-size: 20px">{{ countData.totalSalesAmount }}</div>
            </div>
          </div>
        </el-col>
      </el-row>
      
    </div>
    <el-row :gutter="10" style="margin: 10px 0">
      <el-col :span="6">
        <div class="card" id="" style="width: 100%; height: 400px">
          <div class="avatar">
            <img class="head" :src="user.avatar || require('@/assets/imgs/avatar.png')" />
            <div class="userinfo">
              <div class="name">{{ user.name || '管理员' }}</div>
            </div>
            <div class="info">
                <div><span>{{ user.email }}</span></div>
                <div><span>{{ user.phone }}</span></div>
            </div>
        </div>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="card" id="line" style="width: 100%; height: 400px"></div>
      </el-col>
      <el-col :span="8">
       
          <div class="card">
            <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">公告列表</div>
            <div>
              <el-timeline  slot="reference">
                <el-timeline-item v-for="item in sortedNotices" :key="item.id" :timestamp="item.time">
                  <el-popover>
                    <span slot="reference"> <el-button @click="drawer = !drawer" style="border:none">
                      {{ item.title }}
                    </el-button></span>
                    <el-drawer :visible.sync="drawer"  :before-close="handleClose" >
                      <span :content="item.content" style="font-weight: bold; font-size: 16px;">{{ item.content }}</span>
                    </el-drawer>
                  </el-popover>
                </el-timeline-item>
              </el-timeline>
            </div>
          </div>
        
      </el-col>
    </el-row>

    <el-row :gutter="10" style="margin: 10px 0">
      <el-col :span="8">
        <div class="card" id="pie" style="width: 100%; height: 400px"></div>
      </el-col>
      <el-col :span="8">
        <div class="card" id="pie3" style="width: 100%; height: 400px"></div>
      </el-col>
      <el-col :span="8">
        <div class="card" id="pie1" style="width: 100%; height: 400px"></div>
      </el-col>
    </el-row>

    <el-row :gutter="10" style="margin: 10px 0">
      <el-col :span="12">
        <div class="card" id="funnel" style="width: 100%; height: 400px"></div>
      </el-col>
      <el-col :span="12">
        <div class="card" id="bar" style="width: 100%; height: 400px"></div>
        <!-- <div class="card" id="pie3" style="width: 100%; height: 400px"></div> -->
      </el-col>

    </el-row>

    
  </div>
</template>

<script>
import * as echarts from 'echarts'
//金额趋势
const lineOption = {
  title: {
    text: '近半年产品成交金额趋势图',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    left: 'left'
  },
  xAxis: {
    type: 'category',
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '金额',
      data: [],
      type: 'line',
      smooth: true
    },
  ]
}
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
//公海状态分布
const pieOption = {
  title: {
    text: '公海用户状态分布',
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
//状态分布
const pieOption1 = {
  title: {
    text: '用户状态分布',
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
//行业分布
const pieOption3 = {
  title: {
    text: '所属行业分布',
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
//用户级别占比
const funnelOption ={
  title: {
    text: '用户级别占比图'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c}%'
  },
  toolbox: {
    feature: {
      dataView: { readOnly: false },
      restore: {},
      saveAsImage: {}
    }
  },
  legend: {
    data: []
  },
  series: [
    {
      name: 'Expected',
      type: 'funnel',
      left: '10%',
      width: '80%',
      label: {
        formatter: '{b}'
      },
      labelLine: {
        show: false
      },
      itemStyle: {
        opacity: 0.7
      },
      emphasis: {
        label: {
          position: 'inside',
          formatter: '{b}Expected: {c}%'
        }
      },
      data: []
    },
    {
      name: 'Actual',
      type: 'funnel',
      left: '10%',
      width: '80%',
      maxSize: '80%',
      label: {
        position: 'inside',
        formatter: '{c}%',
        color: '#fff'
      },
      itemStyle: {
        opacity: 0.5,
        borderColor: '#fff',
        borderWidth: 2
      },
      emphasis: {
        label: {
          position: 'inside',
          formatter: '{b}Actual: {c}%'
        }
      },
      data: [],
      z: 100
    }
  ]
}


export default {
  name: 'Home',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: [],
      drawer: false,
      countData: {}
    }
  },
  mounted() {
    this.$request.get('/countData').then(res => {
      this.countData = res.data
    })
    // 折线图
    let linetDom = document.getElementById('line');
    let lineChart = echarts.init(linetDom);

    // 柱状图
    let bartDom = document.getElementById('bar');
    let barChart = echarts.init(bartDom);

    // 公海用户状态饼图
    let pieDom = document.getElementById('pie');
    let pieChart = echarts.init(pieDom);
    //成交用户状态饼图
    let pieDom1 = document.getElementById('pie1');
    let pieChart1 = echarts.init(pieDom1);
   
    //所属行业分布
    let pieDom3 = document.getElementById('pie3');
    let pieChart3 = echarts.init(pieDom3);
      //所属行业分布
    let funnelDom = document.getElementById('funnel');
    let funnelChart = echarts.init(funnelDom);

    //折线数据
    this.$request.get('/saleData').then(res => {
      lineOption.xAxis.data = res.data?.map(v => v.name) || []
      lineOption.series[0].data = res.data?.map(v => v.value) || []
      lineChart.setOption(lineOption)
    })
     //柱状图数据
    this.$request.get('/userData').then(res => {
      barOption.xAxis[0].data = res.data?.map(v => v.address) || [];
      barOption.series[0].data = res.data?.map(v => v.count) || [];
      barChart.setOption(barOption);
    });
    
    //公海用户状态数据
    this.$request.get('/StoreData').then(res => {
      pieOption.series[0].data = res.data || []
      pieChart.setOption(pieOption)
    })
    //成交用户状态数据
    this.$request.get('/StatusData').then(res => {
      pieOption1.series[0].data = res.data || []
      pieChart1.setOption(pieOption1)
    })
     //所属行业分布
     this.$request.get('/IndustryData').then(res => {
      pieOption3.series[0].data = res.data || []
      pieChart3.setOption(pieOption3)
    })
     //用户级别分布
     this.$request.get('/LevelData').then(res => {
      funnelOption.series[0].data = res.data || []
      funnelChart.setOption(funnelOption)
    })

    //公告
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })
  },
  computed: {
  sortedNotices() {
    return this.notices.slice().sort((a, b) => new Date(b.time) - new Date(a.time));
  }
},
methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      }
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
.card {
  max-height: 400px;
  overflow-y: auto;
}

.card::-webkit-scrollbar {
  display: none; /* 隐藏滚动条 */
}

.card:hover::-webkit-scrollbar {
  display: auto; /* 鼠标悬停时显示滚动条 */
}

</style>

