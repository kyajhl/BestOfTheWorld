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

              <el-form-item label="用户名" prop="teacherId">
                <el-input v-model="teacher.teacherId" :disabled="true"></el-input>
              </el-form-item>

              <el-form-item label="密码" prop="password">
                <el-input v-model="teacher.password" show-password></el-input>
              </el-form-item>

              <el-form-item label="姓名" prop="name">
                <el-input v-model="teacher.name"></el-input>
              </el-form-item>

              <el-form-item label="电话" prop="telephone">
                <el-input v-model="teacher.telephone"></el-input>
              </el-form-item>

              <el-form-item label="生日" prop="birthday">
                <el-col :span="10" style="margin-left: -10px">
                  <el-date-picker type="date" placeholder="选择日期" v-model="teacher.birthday"
                                  style="width: 100%;"></el-date-picker>
                </el-col>
              </el-form-item>

              <el-form-item label="性别" prop="sex">
                <el-radio-group v-model="teacher.sex">
                  <el-radio label="男"></el-radio>
                  <el-radio label="女"></el-radio>
                </el-radio-group>
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
      let checkTelephone = (rule, value, callback) => {
        // 电话的正则表达式
        let reg = /^1\d{10}$/;
        if (!reg.test(value)) {
          return callback(new Error("电话格式错误"));
        }
        callback();
      };
      return {
        teacher: {
          teacherId: '',
          password: '',
          name: '',
          telephone: '',
          sex: '',
          birthday: '',
        },
        rules: {
          teacherId: [
            {required: true, message: '用户名不能为空', trigger: 'blur'},
          ],
          password: [
            {required: true, message: '密码不能为空', trigger: 'blur'},
          ],
          name: [
            {required: true, message: '姓名不能为空', trigger: 'blur'},
          ],
          telephone: [
            {required: true, message: '电话不能为空', trigger: 'blur'},
            {validator: checkTelephone, trigger: 'blur'}
          ],
          birthday: [
            {type: 'date', required: true, message: '日期不能为空', trigger: 'change'}
          ],
          sex: [
            {required: true, message: '性别不能为空', trigger: 'change'}
          ],
        }
      }
    },
    computed: {
      ...mapGetters(["name", "roleId", "realName", "telephone", "sex", "birthday", "password"]),
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
                this.$message.error('更新用户失败');
              }
            );

            console.log('submit');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },

      // 把日期字符串转换为日期
      getDateBySplit(currentTime) {
        let dateArray = currentTime.split('-');
        let year = dateArray[0];
        let month = dateArray[1] - 1;
        let day = dateArray[2];
        return new Date(year, month, day);
      },


    },
    mounted() {
      // 当个人信息组件挂载，首先获取用户信息
      this.teacher.teacherId = this.name;
      this.teacher.password = this.password;
      this.teacher.name = this.realName;
      this.teacher.telephone = this.telephone;
      this.teacher.birthday = this.getDateBySplit(this.birthday);
      this.teacher.sex = this.sex;
    }
  }
</script>

<style type="text/css" scoped>
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
</style>
