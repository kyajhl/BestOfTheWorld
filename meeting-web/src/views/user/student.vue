<template>
  <div>
    <div>
      <el-row :gutter="20">
        <el-col :span="4">
          <div class="grid-content"></div>
        </el-col>
        <el-col :span="16">
          <div class="grid-content" style="margin-top: 60px">
            <el-form ref="student" :model="student" :rules="rules" label-width="80px" label-position="left">

              <el-form-item label="用户名" prop="studentId">
                <el-input v-model="student.studentId" :disabled="true"></el-input>
              </el-form-item>

              <el-form-item label="密码" prop="password">
                <el-input v-model="student.password" show-password></el-input>
              </el-form-item>

              <el-form-item label="姓名" prop="name">
                <el-input v-model="student.name"></el-input>
              </el-form-item>

              <el-form-item label="专业" prop="major">
                <el-input v-model="student.major"></el-input>
              </el-form-item>

              <el-form-item label="专业班级" prop="majorClass">
                <el-input v-model="student.majorClass"></el-input>
              </el-form-item>

              <el-form-item label="电话" prop="telephone">
                <el-input v-model="student.telephone"></el-input>
              </el-form-item>

              <el-form-item label="生日" prop="birthday">
                <el-col :span="10" style="margin-left: -10px">
                  <el-date-picker type="date" placeholder="选择日期" v-model="student.birthday"
                                  style="width: 100%;"></el-date-picker>
                </el-col>
              </el-form-item>

              <el-form-item label="性别" prop="sex">
                <el-radio-group v-model="student.sex">
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
  import {mapGetters} from 'vuex';
  import {updateInformation} from '@/api/user'
  import {removeToken} from "@/utils/auth";

  export default {
    name: "student",
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
        student: {
          studentId: '',
          password: '',
          name: '',
          major: '',
          telephone: '',
          sex: '',
          majorClass: '',
          birthday: '2001-12-01',
        },
        rules: {
          studentId: [
            {required: true, message: '用户名不能为空', trigger: 'blur'},
          ],
          password: [
            {required: true, message: '密码不能为空', trigger: 'blur'},
          ],
          name: [
            {required: true, message: '姓名不能为空', trigger: 'blur'},
          ],
          major: [
            {required: true, message: '专业不能为空', trigger: 'blur'},
          ],
          telephone: [
            {required: true, message: '电话不能为空', trigger: 'blur'},
            {validator: checkTelephone, trigger: 'blur'}
          ],
          majorClass: [
            {required: true, message: '专业班级不能为空', trigger: 'blur'},
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
      ...mapGetters(["name", "roleId", "realName", "major", "majorClass",
        "telephone", "sex", "birthday", "password"]),
    },
    methods: {
      // 提交表单，保存用户信息
      onSubmit() {
        this.$refs.student.validate((valid) => {
          if (valid) {
            // 调用更新个人信息接口
            updateInformation(this.student, this.roleId).then(
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
      }
    },
    mounted() {
      // 当个人信息组件挂载，首先获取用户信息
      this.student.studentId = this.name;
      this.student.password = this.password;
      this.student.name = this.realName;
      this.student.major = this.major;
      this.student.majorClass = this.majorClass;
      this.student.telephone = this.telephone;
      this.student.birthday = this.getDateBySplit(this.birthday);
      this.student.sex = this.sex;
    },
  }
</script>

<style type="text/css" scoped>
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
</style>
