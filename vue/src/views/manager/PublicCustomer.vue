<template>
  <div>
    <div class="search">
      <el-input
        placeholder="请输入名称查询"
        style="width: 200px"
        v-model="name"
      ></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)"
        >查询</el-button
      >
      <el-button type="warning" plain style="margin-left: 10px" @click="reset"
        >重置</el-button
      >
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增公海用户信息</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>
    <div class="table">
      <el-table :data="tableData" style="width: 100%" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column fixed prop="id" label="序号" width="70">
        </el-table-column>
        <el-table-column prop="name" label="用户名称" width="120">
        </el-table-column>
        <el-table-column prop="tel" label="用户电话" width="120"> </el-table-column>
        <el-table-column prop="email" label="用户邮箱" width="120"> 
        </el-table-column>
        <el-table-column prop="address" label="用户地址" width="300" >
        </el-table-column>
        <el-table-column prop="user" label="联系人" width="120">
        </el-table-column>
        <el-table-column prop="phone" label="联系人电话" width="120">
        </el-table-column>
        <el-table-column prop="status" label="用户状态" width="120">
          <template v-slot="scope">
            <el-tag type="success" v-if="scope.row.status === '跟进中用户'">跟进中用户</el-tag>
            <el-tag type="info" v-if="scope.row.status === '意向用户'">意向用户</el-tag>
            <el-tag type="primary" v-if="scope.row.status === '潜在用户'">潜在用户</el-tag>
            <el-tag type="danger" v-if="scope.row.status === '无意向'">无意向</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="level" label="用户级别" width="120">
          <template v-slot="scope">
            <el-tag type="success" v-if="scope.row.level === '重要用户'">重要用户</el-tag>
            <el-tag type="info" v-if="scope.row.level === '一般用户'">一般用户</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="source" label="用户来源" width="120">
        </el-table-column>
        <el-table-column prop="industry" label="所属行业" width="120">
        </el-table-column>
        <el-table-column prop="details" label="用户详情" width="120">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="220">
          <template slot-scope="scope">
            <el-button type="primary" size="small" plain @click="handleNext" :disabled="scope.row.status === '无意向'"
              >跟进</el-button
            >
            <el-button
              type="primary"
              size="small"
              plain
              @click="handleEdit(scope.row)"
              :disabled="scope.row.status === '无意向'"
              >编辑</el-button
            >
            <el-button
              type="danger"
              size="small"
              plain
              @click="del(scope.row.id)"
              >删除</el-button
            >
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
          :total="total"
        >
        </el-pagination>
      </div>
    </div>
    <!-- 新增 ：更新 -->
    <el-dialog
      title="公海用户"
      :visible.sync="fromVisible"
      width="40%"
      :close-on-click-modal="false"
      destroy-on-close
    >
      <el-form
        :model="form"
        label-width="100px"
        style="padding-right: 50px"
        :rules="rules"
        ref="formRef"
      >
        <el-form-item label="用户名称" prop="name">
          <el-input v-model="form.name" placeholder="用户名称"></el-input>
        </el-form-item>
        <el-form-item label="用户电话" prop="tel">
          <el-input v-model="form.tel" placeholder="用户电话"></el-input>
        </el-form-item>
        <el-form-item label="用户邮箱" prop="email">
          <el-input v-model="form.email" placeholder="用户邮箱"></el-input>
        </el-form-item>
        <el-form-item label="用户地址" prop="address">
          <el-input v-model="form.address" placeholder="用户地址"></el-input>
        </el-form-item>
        <el-form-item label="联系人" prop="user">
          <el-input v-model="form.user" placeholder="联系人"></el-input>
        </el-form-item>
        <el-form-item label="联系人电话" prop="phone">
          <el-input v-model="form.phone" placeholder="联系人电话"></el-input>
        </el-form-item>
        <el-form-item label="用户状态" prop="status">
          <el-select style="width: 100%" v-model="form.status">
            <el-option
              v-for="item in ['潜在用户', '意向用户', '跟进中用户','无意向']"
              :key="item"
              :value="item"
              :label="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户级别" prop="level">
          <el-select style="width: 100%" v-model="form.level">
            <el-option
              v-for="item in ['重要用户', '一般用户']"
              :key="item"
              :value="item"
              :label="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户来源" prop="source">
          <el-input v-model="form.source" placeholder="用户来源"></el-input>
        </el-form-item>
        <el-form-item label="所属行业" prop="industry">

            <el-select style="width: 100%" v-model="form.industry">
              <el-option
                v-for="item in ['交通运输业', '金融业','制造业','贸易/批发/零售/租赁业','房地产业','IT/通信/电子/互联网']"
                :key="item"
                :value="item"
                :label="item"
              ></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="用户详情" prop="details">
          <el-input v-model="form.details" placeholder="用户详情"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 跟进 -->
    <el-dialog
      title="跟进记录"
      :visible.sync="fromVisible1"
      width="40%"
      :close-on-click-modal="false"
      destroy-on-close
    >
      <el-form
        :model="form"
        label-width="100px"
        style="padding-right: 50px"
        :rules="rules"
        ref="formRef"
      >
        <el-form-item label="跟进记录" prop="record">
          <el-input v-model="form.record" placeholder="跟进记录"></el-input>
        </el-form-item>
        <el-form-item label="跟进主体" prop="flname">
          <el-input v-model="form.flname" placeholder="跟进主体"></el-input>
        </el-form-item>
        <el-form-item label="跟进商机" prop="business">
          <el-input v-model="form.business" placeholder="跟进商机"></el-input>
        </el-form-item>
        <el-form-item label="联系人" prop="contacts">
          <el-input v-model="form.contacts" placeholder="联系人"></el-input>
        </el-form-item>
        <el-form-item label="跟进方式" prop="way">
          <el-select style="width: 100%" v-model="form.way">
            <el-option
              v-for="item in ['微信沟通', '电话沟通', '线下拜访']"
              :key="item"
              :value="item"
              :label="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="跟进内容" prop="content">
          <el-select style="width: 100%" v-model="form.content">
            <el-option
              v-for="item in ['线索筛选', '需求挖掘', '方案沟通', '报价']"
              :key="item"
              :value="item"
              :label="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="跟进时间" prop="time">
          <el-date-picker
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            placeholder="跟进时间"
            v-model="form.time"
            style="width: 100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="跟进人" prop="responsible">
          <el-input v-model="form.responsible" placeholder="跟进人"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
  <script>
export default {
  name: "PublicCustomer",
  data() {
    return {
      tableData: [], // 所有的数据
      pageNum: 1, // 当前的页码
      pageSize: 10, // 每页显示的个数
      total: 0,
      name: null,
      fromVisible: false,
      fromVisible1: false,
      form: {},
      responsible: JSON.parse(localStorage.getItem("xm-user") || "{}"),
      rules: {
        name: [{ required: true, message: "请输入必填项" }],
        address: [{ required: true, message: "请输入必填项" }],
        tel: [{ required: true, message: "请输入必填项" }],
      },
      ids: [],
    };
  },
  created() {
    this.load(1);
  },
  methods: {
    handleAdd() {
      // 新增数据
      this.form = {}; // 新增数据的时候清空数据
      this.fromVisible = true; // 打开弹窗
    },
    handleNext() {
      // 跟进记录
      this.form = {}; // 给form对象赋值  注意要深拷贝数据
      this.fromVisible1 = true; // 打开弹窗
    },
    handleEdit(row) {
      // 编辑数据
      this.form = JSON.parse(JSON.stringify(row)); // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true; // 打开弹窗
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          if (this.fromVisible) {
            // 保存客户表
            this.$request({
              url: this.form.id
                ? "/publiccustomer/update"
                : "/publiccustomer/add",
              method: this.form.id ? "PUT" : "POST",
              data: this.form,
            }).then((res) => {
              if (res.code === "200") {
                // 表示成功保存
                this.$message.success("保存成功");
                this.load(1);
                this.fromVisible = false;
              } else {
                this.$message.error(res.msg); // 弹出错误的信息
              }
            });
          } else if (this.fromVisible1) {
            // 保存跟进记录
            this.$request({
              url: "/publiccustomer/flowup",
              method: "POST",
              data: this.form,
            }).then((res) => {
              if (res.code === "200") {
                // 表示成功保存
                this.$message.success("保存成功");
                this.load(1);
                this.fromVisible1 = false;
              } else {
                this.$message.error(res.msg); // 弹出错误的信息
              }
            });
          }
        }
      });
    },
    del(id) {
      // 单个删除
      this.$confirm("您确定删除吗？", "确认删除", { type: "warning" })
        .then((response) => {
          this.$request.delete("/publiccustomer/delete/" + id).then((res) => {
            if (res.code === "200") {
              // 表示操作成功
              this.$message.success("操作成功");
              this.load(1);
            } else {
              this.$message.error(res.msg); // 弹出错误的信息
            }
          });
        })
        .catch(() => {});
    },
    handleSelectionChange(rows) {
      // 当前选中的所有的行数据
      this.ids = rows.map((v) => v.id);
    },
    delBatch() {
      // 批量删除
      if (!this.ids.length) {
        this.$message.warning("请选择数据");
        return;
      }
      this.$confirm("您确定批量删除这些数据吗？", "确认删除", {
        type: "warning",
      })
        .then((response) => {
          this.$request
            .delete("/publiccustomer/delete/batch", { data: this.ids })
            .then((res) => {
              if (res.code === "200") {
                // 表示操作成功
                this.$message.success("操作成功");
                this.load(1);
              } else {
                this.$message.error(res.msg); // 弹出错误的信息
              }
            });
        })
        .catch(() => {});
    },
    load(pageNum) {
      // 分页查询
      if (pageNum) this.pageNum = pageNum;
      this.$request
        .get("/publiccustomer/selectPage", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
          },
        })
        .then((res) => {
          this.tableData = res.data?.list;
          this.total = res.data?.total;
        });
    },
    reset() {
      this.name = null;
      this.load(1);
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum);
    },
  },
};
</script>
  
  <style scoped>
</style>