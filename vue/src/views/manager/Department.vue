<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入关键字查询" style="width: 200px" v-model="name"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
    </div>

    <div class="table">
      <el-table row-key="id" default-expand-all :data="tableData" strip>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="部门名称"></el-table-column>
        <el-table-column prop="address" label="部门地址"></el-table-column>
        <el-table-column prop="pid" label="父级ID"></el-table-column>
        <el-table-column prop="level" label="层级"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog title="部门表" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="form.name" placeholder="部门名称"></el-input>
        </el-form-item>
        <el-form-item label="部门地址" prop="address">
          <el-input v-model="form.address" placeholder="部门地址"></el-input>
        </el-form-item>
        <el-form-item label="父级" prop="pid">
          <el-select ref="selectTree" v-model="form.pid"  style="width: 100%;">
            <el-option
                v-for="item in departmentList"
                :label="item.name"
                :value="item.id"
                :key="item"
                style="display: none;"/>
            <el-tree
                :data="tableData"
                :props="{children: 'children', label: 'name'}"
                highlight-current
                @node-click="handleNodeClick"
                default-expand-all />
          </el-select>
        </el-form-item>
        <el-form-item label="层级" prop="level">
          <el-input v-model="form.level" placeholder="层级"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>
<script>
export default {
  name: "Department",
  data() {
    return {
      tableData: [],  // 所有的数据
      departmentList: [],
      name: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
      },
      ids: []
    }
  },
  created() {
    this.load()
    // 先查出扁平的部门数组
    this.$request.get('/department/selectAll').then(res => {
      this.departmentList = res.data
    })
  },
  methods: {
    // node 就是department对象
    handleNodeClick(node) {
      this.form.departmentId = node.id
      this.$refs.selectTree.blur()
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/department/update' : '/department/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load()
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/department/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load() {  // 分页查询
      this.$request.get('/department/selectTree', {
        params: {
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data
      })
    },
    reset() {
      this.name = null
      this.load()
    },
  }
}
</script>

<style scoped>
 .el-table__indent {
  padding-left: 16px !important;
}
</style>