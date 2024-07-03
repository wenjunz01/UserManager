<template>
    <div>
      <div class="operation">
        <el-button type="danger" plain @click="delBatch">批量删除</el-button>
      </div>
  
      <div class="table">
        <el-table :data="tableData" strip @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"></el-table-column>
          <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
          <el-table-column prop="record" label="跟进记录"></el-table-column>
          <el-table-column prop="flname" label="跟进主体"></el-table-column>
          <el-table-column prop="business" label="跟进商机"></el-table-column>
          <el-table-column prop="contacts" label="联系人"></el-table-column>
          <el-table-column prop="way" label="跟进方式">
            <template v-slot="scope">
              <el-tag type="success" v-if="scope.row.way === '微信沟通'">微信沟通</el-tag>
              <el-tag type="info" v-if="scope.row.way === '电话沟通'">电话沟通</el-tag>
              <el-tag type="primary" v-if="scope.row.way === '线下拜访'">线下拜访</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="content" label="跟进内容">
            <template v-slot="scope">
              <el-tag type="info" v-if="scope.row.content === '线索筛选'">线索筛选</el-tag>
              <el-tag type="success" v-if="scope.row.content === '需求挖掘'">需求挖掘</el-tag>
              <el-tag type="warning" v-if="scope.row.content === '方案沟通'">方案沟通</el-tag>
              <el-tag type="primary" v-if="scope.row.content === '报价'">报价</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="time" label="跟进时间"></el-table-column>
          <el-table-column prop="responsible" label="跟进人"></el-table-column>
          <el-table-column label="操作" align="center" width="220">
            <template v-slot="scope">
              <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
  
        <div class="pagination">
          <el-pagination
              background
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[5, 10, 20]"
              :page-size="pageSize"
              layout="total, prev, pager, next"
              :total="total">
          </el-pagination>
        </div>
      </div>
    </div>
  </template>
  <script>
  export default {
    name: "FollowUp",
    data() {
      return {
        tableData: [],  // 所有的数据
        pageNum: 1,   // 当前的页码
        pageSize: 10,  // 每页显示的个数
        total: 0,
        name: null,
        responsible: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      }
    },
    created() {
      this.load(1)
    },
    methods: {
      del(id) {   // 单个删除
        this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
          this.$request.delete('/followup/delete/' + id).then(res => {
            if (res.code === '200') {   // 表示操作成功
              this.$message.success('操作成功')
              this.load(1)
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }).catch(() => {
        })
      },
      handleSelectionChange(rows) {   // 当前选中的所有的行数据
        this.ids = rows.map(v => v.id)
      },
      delBatch() {   // 批量删除
        if (!this.ids.length) {
          this.$message.warning('请选择数据')
          return
        }
        this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
          this.$request.delete('/followup/delete/batch', {data: this.ids}).then(res => {
            if (res.code === '200') {   // 表示操作成功
              this.$message.success('操作成功')
              this.load(1)
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }).catch(() => {
        })
      },
      load(pageNum) {  // 分页查询
        if (pageNum) this.pageNum = pageNum
        this.$request.get('/followup/selectPage', {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
          }
        }).then(res => {
          this.tableData = res.data?.list
          this.total = res.data?.total
        })
      },
      reset() {
        this.flname = null
        this.load(1)
      },
      handleCurrentChange(pageNum) {
        this.load(pageNum)
      },
    }
  }
  </script>
  
  <style scoped>
  
  </style>