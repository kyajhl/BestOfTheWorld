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
          placeholder="手机号"
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

      <el-form-item prop="radioCheck">
        <div class="radioCheck">
          <el-radio v-model="loginForm.radioCheck" label="1" border size="medium">学生</el-radio>
          <el-radio v-model="loginForm.radioCheck" label="2" border size="medium">教师</el-radio>
          <el-radio v-model="loginForm.radioCheck" label="3" border size="medium">企业</el-radio>
        </div>
      </el-form-item>

      <el-button :loading="loading"
                 type="primary"
                 style="width:100%;margin-bottom:30px;"
                 @click.native.prevent="handleLogin"
      >注册
      </el-button>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: 'Register',
    data() {
      return {
        loginForm: {
          username: '',
          password: '',
          radioCheck: '',
        },
        checked: false,
        loginRules: {
          username: [{required: true, trigger: 'blur', message: "请输入手机号"}],
          password: [{required: true, trigger: 'blur', message: "请输入密码"}],
          radioCheck: [{required: true, trigger: 'blur', message: "请选择角色"}]
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
            this.$store.dispatch('user/register', this.loginForm).then(
              response => {
                // 注册成功
                this.loading = false
              },
              error => {
                // 注册失败
                this.loading = false
              }
            ).catch(() => {
              this.loading = false;
            })
          } else {
            console.log('手机号或密码未输入');
            return false
          }
        })
      }
    },
    mounted() {

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
