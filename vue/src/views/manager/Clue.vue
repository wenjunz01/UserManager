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
      <el-button type="primary" plain @click="handleAdd">新增线索信息</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table
        :data="tableData"
        strip
        @selection-change="handleSelectionChange"
      >
        <el-table-column
          type="selection"
          width="55"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="id"
          label="序号"
          width="70"
          align="center"
          sortable
        ></el-table-column>
        <el-table-column prop="name" label="用户名称"></el-table-column>
        <el-table-column prop="tel" label="电话"></el-table-column>
        <el-table-column prop="user" label="联系人"></el-table-column>
        <el-table-column prop="department" label="部门"></el-table-column>
        <el-table-column prop="duties" label="职务"></el-table-column>
        <el-table-column prop="sources" label="线索来源"></el-table-column>
        <el-table-column prop="cdetails" label="线索详情"></el-table-column>
        <el-table-column prop="status" label="线索状态">
          <template v-slot="scope">
            <el-tag type="success" v-if="scope.row.status === '已转换'">已转换</el-tag>
            <el-tag type="primary" v-if="scope.row.status === '未转换'">未转换</el-tag>
           
          </template>
        </el-table-column>
        <el-table-column prop="fallback" label="线索回退原因"></el-table-column>
        <el-table-column label="操作" align="center" width="280">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleNext"
            :disabled="scope.row.status === '已转换'"
            >转换</el-button
          >
            <el-button
              size="mini"
              type="primary"
              plain
              @click="handleEdit(scope.row)"
              :disabled="scope.row.status === '已转换'"
              >维护</el-button
            >
            <el-button
              size="mini"
              type="danger"
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

<!-- 新增：维护 -->
    <el-dialog
      title="线索"
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
        <el-form-item label="联系人" prop="user">
          <el-input v-model="form.user" placeholder="联系人"></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="department">
          <el-input v-model="form.department" placeholder="部门"></el-input>
        </el-form-item>
        <el-form-item label="职务" prop="duties">
          <el-input v-model="form.duties" placeholder="职务"></el-input>
        </el-form-item>
        <el-form-item label="线索来源" prop="sources">
          <el-input v-model="form.sources" placeholder="线索来源"></el-input>
        </el-form-item>
        <el-form-item label="线索详情" prop="cdetails">
          <el-input v-model="form.cdetails" placeholder="线索详情"></el-input>
        </el-form-item>
        <el-form-item label="线索状态" prop="status">
          <el-select style="width: 100%" v-model="form.status">
            <el-option
              v-for="item in ['已转换', '未转换']"
              :key="item"
              :value="item"
              :label="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="线索回退原因" prop="fallback">
          <el-select style="width: 100%" v-model="form.fallback">
            <el-option
              v-for="item in ['联系不上用户', '用户无需求','未满足用户需求']"
              :key="item"
              :value="item"
              :label="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 转换 -->
    <el-dialog title="公海用户" :visible.sync="fromVisible3" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
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
        <el-button @click="fromVisible3 = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
  <script>
export default {
  name: "Clue",
  data() {
    return {
      tableData: [], // 所有的数据
      pageNum: 1, // 当前的页码
      pageSize: 10, // 每页显示的个数
      total: 0,
      name: null,
      fromVisible: false,
      fromVisible1: false,
      fromVisible3: false,
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
    handleEdit(row) {
      // 编辑数据
      this.form = JSON.parse(JSON.stringify(row)); // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true; // 打开弹窗
    },
    handleNext() {
      // 跟进记录
      this.form = {}; // 给form对象赋值  注意要深拷贝数据
      this.fromVisible3 = true; // 打开弹窗
    },
   
    handleConvert(){
      this.form = {}; // 新增数据的时候清空数据
      this.fromVisible3 = true; // 打开弹窗
    },
    save() {
  this.$refs.formRef.validate((valid) => {
    if (valid) {
      let requestData = {
        url: "",
        method: "",
        successMessage: "保存成功",
        visibleFlag: "",
      };
      
      if (this.fromVisible) {
        requestData.url = this.form.id ? "/clue/update" : "/clue/add";
        requestData.method = this.form.id ? "PUT" : "POST";
        requestData.visibleFlag = "fromVisible";
      } else if (this.fromVisible3) {
        requestData.url = "/clue/flowup";
        requestData.method = "POST";
        requestData.visibleFlag = "fromVisible3";
      }
      this.$request({
        url: requestData.url,
        method: requestData.method,
        data: this.form,
      }).then((res) => {
        if (res.code === "200") {
          this.$message.success(requestData.successMessage);
          this.load(1);
          this[requestData.visibleFlag] = false;
        } else {
          this.$message.error(res.msg);
        }
      });
    }
  });
},
    del(id) {
      // 单个删除
      this.$confirm("您确定删除吗？", "确认删除", { type: "warning" })
        .then((response) => {
          this.$request.delete("/clue/delete/" + id).then((res) => {
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
            .delete("/clue/delete/batch", { data: this.ids })
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
        .get("/clue/selectPage", {
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