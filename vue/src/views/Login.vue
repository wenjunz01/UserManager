<template>
  <div class="container">
    <div style="width: 360px; padding: 50px 30px; background-color: rgba(0,0,0,.3); border-radius: 5px; box-shadow: 0 2px 10px rgba(0,0,0,.1)">
      <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #046fdc"><b>基于数据分析的用户关系管理系统</b></div>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" placeholder="请输入密码" show-password  v-model="form.password"></el-input>
        </el-form-item>

        <el-form-item>
          <el-select style="width: 100%" v-model="form.role">
            <el-option v-for="item in [{ name: '管理员', value: 'ADMIN' }, { name: '员工', value: 'STAFF' }]" :key="item.value" :value="item.value" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <div style="display: flex">
            <el-input style="flex: 1" v-model="form.code" placeholder="请输入验证码"></el-input>
            <div style="flex: 1; margin-left: 10px"> <img  @click="getCode()" :src="codeImg" style="width: 100%; height: 30px"/></div>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%; background-color: #176fd6; border-color: #176fd6; color: white" @click="login">登 录</el-button>
        </el-form-item>
        <div style="display: flex; align-items: center">
          <div style="flex: 1"></div>
          <div style="flex: 1; text-align: right">
          </div>
        </div>
      </el-form>
    </div>

  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      dialogVisible: true,
      form: { role: 'ADMIN' },
      codeImg: '',
      uuid: '',
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ]
      }
    }
  },
  created() {
    //生成随机码
    this.getCode()
  },
  methods: {
    getCode() {
      this.uuid = generateUUID()
      this.codeImg = this.$baseUrl + '/validateCode?key=' + this.uuid
    },
    login() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.form.key = this.uuid
          this.$request.post('/login', this.form).then(res => {
            if (res.code === '200') {
              this.$router.push('/')  // 跳转主页
              this.$message.success('登录成功')
              localStorage.setItem("xm-user", JSON.stringify(res.data))  // 存储用户数据
            } else {
              this.getCode()
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
  }
}

// 生成UUID
function generateUUID() {
  let d = new Date().getTime();
  if (window.performance && typeof window.performance.now === "function") {
    d += performance.now(); //use high-precision timer if available
  }
  let uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    let r = (d + Math.random() * 16) % 16 | 0;
    d = Math.floor(d / 16);
    return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
  });
  return uuid;
}

</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/imgs/bg1.jpg");
  background-size: cover;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
}
a {
  color: #2a60c9;
}
</style>