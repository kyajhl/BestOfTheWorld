<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
             label-position="left">

      <div class="title-container">
        <h3 class="title">高校实训实习管理平台</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user"/>
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
        </span>
      </el-form-item>

      <el-checkbox v-model="checked">记住登录信息</el-checkbox>

      <div class="radioCheck">
        <el-radio v-model="loginForm.radioCheck" label="1" border size="medium">学生</el-radio>
        <el-radio v-model="loginForm.radioCheck" label="2" border size="medium">教师</el-radio>
        <el-radio v-model="loginForm.radioCheck" label="3" border size="medium">企业</el-radio>
      </div>

      <el-button :loading="loading"
                 type="primary"
                 style="width:100%;margin-bottom:30px;"
                 @click.native.prevent="handleLogin"
      >登录
      </el-button>
    </el-form>
  </div>
</template>

<script>
  import {validUsername} from '@/utils/validate'

  export default {
    name: 'Login',
    data() {
      return {
        loginForm: {
          username: '',
          password: '',
          radioCheck: '',
        },
        checked: false,
        loginRules: {
          username: [{required: true, trigger: 'blur', message: "请输入用户名"}],
          password: [{required: true, trigger: 'blur', message: "请输入密码"}]
        },
        loading: false,
        passwordType: 'password',
        redirect: undefined,
      }
    },
    watch: {
      $route: {
        handler: function (route) {
          this.redirect = route.query && route.query.redirect
        },
        immediate: true
      },
      'loginForm.radioCheck'() {
        console.log(this.loginForm);
      }
    },
    methods: {
      showPwd() {
        if (this.passwordType === 'password') {
          this.passwordType = ''
        } else {
          this.passwordType = 'password'
        }
        this.$nextTick(() => {
          this.$refs.password.focus()
        })
      },
      handleLogin() {
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            this.loading = true;
            this.$store.dispatch('user/login', this.loginForm).then(() => {

              // 保存登录信息
              if (this.checked === true) {
                localStorage.setItem("username", this.loginForm.username);
                localStorage.setItem("password", this.loginForm.password);
                localStorage.setItem("radioCheck", this.loginForm.radioCheck);
                localStorage.setItem("checked", JSON.stringify(this.checked));
              }
              // 登录成功跳转首页
              this.$message.success("登陆成功");
              this.$router.push({path: this.redirect || '/'});
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          } else {
            console.log('用户名或密码未输入');
            return false
          }
        })
      }
    },
    mounted() {
      // 组件挂载就获取用户登录信息
      this.loginForm.username = localStorage.getItem("username");
      this.loginForm.password = localStorage.getItem("password");
      this.loginForm.radioCheck = localStorage.getItem("radioCheck");
      this.checked = JSON.parse(localStorage.getItem("checked"));
    }
  }
</script>

<style lang="scss">
  $bg: #283443;
  $light_gray: #fff;
  $cursor: #fff;

  @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input {
      color: $cursor;
    }
  }

  /* reset element-ui css */
  .login-container {
    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;

      input {
        background: transparent;
        border: 0px;
        // -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;

        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }

    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
    }
  }
</style>

<style lang="scss" scoped>
  $bg: #2d3a4b;
  $dark_gray: #889aa4;
  $light_gray: #eee;

  .login-container {
    min-height: 100%;
    width: 100%;
    background-color: $bg;
    overflow: hidden;

    background-image: url(~@/assets/1.jpg);
    background-size: 100% 100%;
    background-repeat: no-repeat;
    display: flex;
    align-items: center;

    .login-form {
      position: relative;
      width: 520px;
      max-width: 100%;
      padding: 35px 35px 10px;
      margin: 0 auto;
      overflow: hidden;
      background-color: #2d3a4b;
      opacity: 0.9;
      border-radius: 7px;
    }

    .svg-container {
      padding: 6px 5px 6px 15px;
      color: $dark_gray;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
    }

    .title-container {
      position: relative;

      .title {
        font-size: 26px;
        color: $light_gray;
        margin: 0px auto 40px auto;
        text-align: center;
        font-weight: bold;
      }
    }

    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
    }
  }
</style>

<style lang="css" scoped>
  .radioCheck {
    margin: 20px 0;
    text-align: center;
  }
</style>
