<template>
  <div>
    <div>
      <el-row :gutter="20">
        <el-col :span="4">
          <div class="grid-content"></div>
        </el-col>
        <el-col :span="16">
          <div class="grid-content" style="margin-top: 60px">
            <el-form ref="teacher" :rules="rules" :model="teacher" label-width="80px" label-position="left">

              <el-form-item label="手机号" prop="mobilephone">
                <el-input v-model="teacher.mobilephone" :disabled="true"></el-input>
              </el-form-item>

              <el-form-item label="姓名" prop="teacherName">
                <el-input v-model="teacher.teacherName"></el-input>
              </el-form-item>

              <el-form-item label="密码" prop="password">
                <el-input v-model="teacher.password" show-password></el-input>
              </el-form-item>

              <el-form-item label="学校" prop="college">
                <el-input v-model="teacher.college"></el-input>
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="onSubmit">修改</el-button>
              </el-form-item>

            </el-form>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="grid-content"></div>
        </el-col>
      </el-row>

    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import {updateInformation} from "@/api/user";
  import {removeToken} from "@/utils/auth";

  export default {
    name: "teacher",
    data() {
      return {
        teacher: {
          mobilephone: '',
          teacherName: '',
          password: '',
          college: ''
        },
        rules: {
          mobilephone: [
            {required: true, message: '手机号不能为空', trigger: 'blur'},
          ],
          password: [
            {required: true, message: '密码不能为空', trigger: 'blur'},
          ],
          teacherName: [
            {required: true, message: '姓名不能为空', trigger: 'blur'},
          ],
          college: [
            {required: true, message: '学校不能为空', trigger: 'blur'},
          ],
        }
      }
    },
    computed: {
      ...mapGetters(["mobilephone", "teacherName", "roleId", "password", "college"]),
    },
    methods: {
      // 提交表单，保存用户信息
      onSubmit() {
        this.$refs.teacher.validate((valid) => {
          if (valid) {
            // 调用更新个人信息接口
            updateInformation(this.teacher, this.roleId).then(
              response => {
                // 判断密码是否匹配，匹配，跳转到首页
                if (response.data === true) {
                  this.$router.replace({path: '/dashboard'});
                  this.$message.success(response.msg);
                } else {
                  // 密码不匹配，直接重新登录
                  this.$store.dispatch("user/logout"); // 删除 token ，清空 store
                  // store 里面的方法调用太慢，router 可能先检测到了还有 token 的情况(猜的)
                  // 就很奇怪，明明把 cookie 删除了，竟然还能获取？
                  // 所以直接清除是最好的
                  removeToken();
                  // 清除完，直接跳转到登录页面
                  this.$router.replace('/login');
                  this.$message.success(response.msg);
                }
              },
              error => {
                this.$message.error('修改信息失败');
              }
            );

            console.log('submit');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
    },
    mounted() {
      // 当个人信息组件挂载，首先获取用户信息
      this.teacher.mobilephone = this.mobilephone;
      this.teacher.teacherName = this.teacherName;
      this.teacher.password = this.password;
      this.teacher.college = this.college;
    }
  }
</script>

<style type="text/css" scoped>
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
</style>
