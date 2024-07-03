<template>
    <div>

  
      <div class="operation">
        <el-button type="primary" plain @click="handleAdd">录入合同信息</el-button>
      </div>
  
      <div class="table">
        <el-table :data="tableData" strip @selection-change="handleSelectionChange">
          <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
          <el-table-column prop="title" label="合同标题"></el-table-column>
          <el-table-column prop="customerName" label="关联用户"></el-table-column>
          <el-table-column prop="tripartiteName" label="关联第三方"></el-table-column>
          <el-table-column prop="business" label="商机信息"></el-table-column>
          <el-table-column prop="price" label="合同金额"></el-table-column>
          <el-table-column prop="money" label="合同金额大写"></el-table-column>
          <el-table-column prop="signingdate" label="合同签订日期"></el-table-column>
          <el-table-column prop="dusdate" label="合同到期日期"></el-table-column>
          <el-table-column prop="cname" label="负责人"></el-table-column>
          <el-table-column prop="phone" label="电话"></el-table-column>
          <el-table-column prop="address" label="用户地址"></el-table-column>
          <el-table-column prop="annex" label="合同附件">
            <template v-slot="scope">
                <div style="display: flex; align-items: center">
                    <template v-if="scope.row.annex">
                        <a :href="scope.row.annex" download>{{ scope.row.annex.split('-')[1] }}</a>
                    </template>
                    <template v-else>
                        <span>无附件</span>
                    </template>
                </div>
            </template>
        </el-table-column>
          <el-table-column prop="status" label="合同状态">
            <template v-slot="scope">
              <el-tag type="success" v-if="scope.row.status === '审核已通过'">审核已通过</el-tag>
              <el-tag type="primary" v-if="scope.row.status === '审核进行中'">审核进行中</el-tag>
              <el-tag type="danger" v-if="scope.row.status === '未通过审核'">未通过审核</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="未通过原因" prop="rejectReason" ></el-table-column>
          <el-table-column label="操作" align="center" width="180">
            <template v-slot="scope">
              <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)" :disabled="scope.row.status === '审核已通过'" >维护</el-button>
              <el-button size="mini" type="danger" plain @click="del(scope.row.id)" v-if="user.role === 'ADMIN'">作废</el-button>
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
  
      <el-dialog title="合同信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
        <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
          <el-form-item label="合同标题" prop="title">
            <el-input v-model="form.title" placeholder="合同标题"></el-input>
          </el-form-item>
          <el-form-item label="关联用户" prop="customerName">
            <el-select style="width: 100%" v-model="form.customerId">
              <el-option v-for="item in customers" :key="item.id" :value="item.id" :label="item.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="关联第三方" prop="tripartiteName">
            <el-select style="width: 100%" v-model="form.tripartiteId">
              <el-option v-for="item in tripartites" :key="item.id" :value="item.id" :label="item.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="商机信息" prop="business">
            <el-input v-model="form.business" placeholder="商机信息"></el-input>
          </el-form-item>
          <el-form-item label="合同金额" prop="price">
            <el-input v-model="form.price" placeholder="合同金额"></el-input>
          </el-form-item>
          <el-form-item label="合同金额大写" prop="money">
            <el-input v-model="form.money" placeholder="合同金额大写"></el-input>
          </el-form-item>
          <el-form-item label="签订日期" prop="signingdate">
            <el-date-picker type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
            v-model="form.signingdate" style="width: 100%"></el-date-picker>
          </el-form-item>
          <el-form-item label="到期日期" prop="dusdate">
            <el-date-picker type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
            v-model="form.dusdate" style="width: 100%"></el-date-picker>
          </el-form-item>
          <el-form-item label="负责人" prop="cname">
            <el-input v-model="form.cname" placeholder="姓名"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="form.phone" placeholder="电话"></el-input>
          </el-form-item>
          <el-form-item label="用户地址" prop="address">
            <el-input v-model="form.address" placeholder="用户地址"></el-input>
          </el-form-item>
          <el-form-item label="合同附件" prop="annex">
            <el-upload
                :action="$baseUrl + '/files/upload'"
                :headers="{ token: user.token }"
                :on-success="handleFileSuccess"
                multiple
                :on-exceed="handleFileExceed"
            >
              <el-button type="primary">上传</el-button>
              <div slot="tip" class="el-upload__tip">文件大小不能超过 100MB</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select style="width: 100%" v-model="form.status">
              <el-option v-for="item in filteredStatus" :key="item" :value="item" :label="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="未通过原因" prop="rejectReason" v-if="form.status === '未通过审核'">
            <el-input v-model="form.rejectReason" placeholder="请输入未通过审核的原因"></el-input>
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
  name: "Contract",
  data() {
    return {
      tableData: [],// 所有的数据
      pageNum: 1,  // 当前的页码
      pageSize: 10, // 每页显示的个数
      total: 0,
      title: null,
      customerName: null,
      tripartiteName: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        customerName: [
          { required: true, message: '请输入必填项' }
        ],
        title: [
          { required: true, message: '请输入必填项' }
        ],
        price: [
          { required: true, message: '请输入必填项' }
        ],
        signingdate: [
          { required: true, message: '请输入必填项' }
        ],
        dusdate: [
          { required: true, message: '请输入必填项' }
        ],

      },
      ids: [],
      customers: [],
      tripartites: [],
    };
  },
  created() {
    this.load(1);
    this.fetchCustomers();
    this.fetchTripartites();
  },

  methods: {
    handleFileExceed(files, fileList) {
    this.$message.warning('文件数量超过限制，最多只能上传1个文件');
  },
    handleFileSuccess(response, file, fileList) {
      this.form.annex = response.data
    },
    fetchCustomers() {
      this.$request.get('/customer/selectAll')
        .then(res => this.customers = res.data)
        .catch(error => console.error(error));
    },
    fetchTripartites() {
      this.$request.get('/tripartite/selectAll')
        .then(res => this.tripartites = res.data)
        .catch(error => console.error(error));
    },
    handleAdd() {
      this.form = {};
      this.fromVisible = true;
    },
    handleEdit(row) {
      this.form = { ...row };
      this.fromVisible = true;
    },
    save() {
      this.$refs.formRef.validate(valid => {
        if (valid) {
          const url = this.form.id ? '/contract/update' : '/contract/add';
          const method = this.form.id ? 'PUT' : 'POST';
          this.$request({ url, method, data: this.form })
            .then(res => {
              if (res.code === '200') {
                this.$message.success('保存成功');
                this.load(1);
                this.fromVisible = false;
              } else {
                this.$message.error(res.msg);
              }
            })
            .catch(error => console.error(error));
        }
      });
    },
    del(id) {
      this.$confirm('是否确实要作废此条数据?', '确认', { type: "warning" })
        .then(() => this.deleteContract(id))
        .catch(() => {});
    },
    deleteContract(id) {
      this.$request.delete(`/contract/delete/${id}`)
        .then(res => {
          if (res.code === '200') {
            this.$message.success('');
            this.load(1);
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch(error => console.error(error));
    },
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.id);
    },
    load(pageNum) {
      this.pageNum = pageNum || this.pageNum;
      this.$request.get('/contract/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          title:this.title,
          name: this.name,
          customerName: this.customerName,
        }
      })
      .then(res => {
        this.tableData = res.data?.list;
        this.total = res.data?.total;
      })
      .catch(error => console.error(error));
    },
    reset() {
      this.title = null;
      this.customerName = null;
      this.load(1);
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum);
    },
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')  // 重新获取下用户的最新信息
    },
    goToPerson() {
      if (this.user.role === 'ADMIN') {
        this.$router.push('/adminPerson')
      } else if (this.user.role === 'STAFF') {
        this.$router.push('/staffPerson')
      }
    },
  },
  computed: {
  isSigningDateExpired() {
    const signingDate = new Date(this.form.signingdate);
    const currentDate = new Date();
    return signingDate < currentDate;
  },
  filteredStatus() {
    if (this.user.role === 'ADMIN') {
      return ['审核已通过', '审核进行中', '未通过审核'];
    } else if (this.user.role === 'STAFF') {
      return ['审核进行中'];
    } else {
      return [];
    }
  },
}
}
  </script>
  <style scoped>
  </style>