<template>
  <div class="manager-container">
    <!--  头部  -->
    <div class="manager-header">
      <div class="manager-header-left">
        <img src="@/assets/imgs/logo.png" />
        <div class="title">用户关系管理</div>
      </div>

      <div class="manager-header-center">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name }}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>

      <div class="manager-header-right">
        <el-dropdown placement="bottom">
          <div class="avatar">
            <img :src="user.avatar || require('@/assets/imgs/avatar.png')" />
            <div>{{ user.name ||  '管理员' }}</div>
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="goToPerson">修改个人信息</el-dropdown-item>
            <el-dropdown-item @click.native="$router.push('/password')">修改账号密码</el-dropdown-item>
            <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!--  主体  -->
    <div class="manager-main">
      <!--  侧边栏  -->
      <div class="manager-main-left">
        <el-menu :default-openeds="['info', 'clue', 'contract','order','system']" router style="border: none" :default-active="$route.path">
          <el-menu-item index="/home">
            <i class="el-icon-s-home"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-submenu index="info">
            <template slot="title">
              <i class="el-icon-user-solid"></i><span class="menu">用户管理</span>
            </template>
            <el-menu-item index="/tripartite">第三方信息</el-menu-item>
            <el-menu-item index="/customer">用户信息</el-menu-item>
            <el-menu-item index="/publiccustomer">用户公海</el-menu-item>

          </el-submenu>
          <el-submenu index="clue" >
            <template slot="title">
              <i class="el-icon-menu"></i><span class="menu">线索管理</span>
            </template>
            <el-menu-item index="/clue" :default-opened="true">线索信息</el-menu-item>
            <el-menu-item index="/plan" :default-opened="true">跟进计划</el-menu-item>
            <el-menu-item index="/followup" :default-opened="true">跟进记录</el-menu-item>
            <el-menu-item index="/followanalysis" :default-opened="true">跟进分析</el-menu-item>
          </el-submenu>
          <el-submenu index="contract">
            <template slot="title">
              <i class="el-icon-menu"></i><span class="menu">合同管理</span>
            </template>
            <el-menu-item index="/contract">合同信息</el-menu-item>
            <el-menu-item index="/goods">产品信息</el-menu-item>
          </el-submenu>
          
          <el-submenu index="order">

            <template slot="title">
              <i class="el-icon-menu"></i><span class="menu">订单管理</span>
            </template>
            <el-menu-item index="/sale">订单信息</el-menu-item>
          </el-submenu>

          <el-submenu index="system" v-if="user.role === 'ADMIN'">
            <template slot="title">
              <i class="el-icon-s-tools"></i><span class="menu">系统管理</span>
            </template>
            <el-menu-item index="/admin">管理员信息</el-menu-item>
            <el-menu-item index="/staff">员工信息</el-menu-item>
            <!-- <el-menu-item index="/department">部门信息</el-menu-item> -->
            <el-menu-item index="/notice">公告信息</el-menu-item>
            <el-menu-item index="/logs">日志信息</el-menu-item>

          </el-submenu>
        </el-menu>
      </div>

      <!--  数据表格  -->
      <div class="manager-main-right">
        <router-view @update:user="updateUser" />
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "Manager",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
    }
  },
  created() {
    if (!this.user.id) {
      this.$router.push('/login')
    }
  },
  methods: {
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // 重新获取下用户的最新信息
    },
    goToPerson() {
      if (this.user.role === 'ADMIN') {
        this.$router.push('/adminPerson')
      } else if (this.user.role === 'STAFF') {
        this.$router.push('/staffPerson')
      }
    },
    logout() {
      localStorage.removeItem('xm-user')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
@import "@/assets/css/manager.css";
</style>