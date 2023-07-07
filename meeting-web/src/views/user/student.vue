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

              <el-form-item label="手机号" prop="mobilephone">
                <el-input v-model="student.mobilephone" :disabled="true"></el-input>
              </el-form-item>

              <el-form-item label="学号" prop="studentId">
                <el-input v-model="student.studentId"></el-input>
              </el-form-item>

              <el-form-item label="姓名" prop="studentName">
                <el-input v-model="student.studentName"></el-input>
              </el-form-item>

              <el-form-item label="密码" prop="password">
                <el-input v-model="student.password" show-password></el-input>
              </el-form-item>

              <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="student.gender">
                  <el-radio label="男"></el-radio>
                  <el-radio label="女"></el-radio>
                </el-radio-group>
              </el-form-item>

              <el-form-item label="生日" prop="birthday">
                <el-col :span="10" style="margin-left: -10px">
                  <el-date-picker type="date" placeholder="选择日期" v-model="student.birthday"
                                  style="width: 100%;"></el-date-picker>
                </el-col>
              </el-form-item>

              <el-form-item label="学校" prop="college">
                <el-input v-model="student.college"></el-input>
              </el-form-item>

              <el-form-item label="专业" prop="major">
                <el-input v-model="student.major"></el-input>
              </el-form-item>

              <el-form-item label="专业班级" prop="majorClass">
                <el-input v-model="student.majorClass"></el-input>
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
      return {
        student: {
          mobilephone: '',
          studentId: '',
          studentName: '',
          password: '',
          gender: '',
          birthday: '2001-12-01',
          college: '',
          major: '',
          majorClass: '',
        },
        rules: {
          mobilephone: [
            {required: true, message: '手机号不能为空', trigger: 'blur'},
          ],
          studentId: [
            {required: true, message: '学号不能为空', trigger: 'blur'},
          ],
          studentName: [
            {required: true, message: '姓名不能为空', trigger: 'blur'},
          ],
          password: [
            {required: true, message: '密码不能为空', trigger: 'blur'},
          ],
          gender: [
            {required: true, message: '性别不能为空', trigger: 'blur'}
          ],
          birthday: [
            {type: 'date', required: true, message: '生日不能为空', trigger: 'blur'}
          ],
          college: [
            {required: true, message: '学校不能为空', trigger: 'blur'}
          ],
          major: [
            {required: true, message: '专业不能为空', trigger: 'blur'},
          ],
          majorClass: [
            {required: true, message: '专业班级不能为空', trigger: 'blur'},
          ],
        }
      }
    },
    computed: {
      ...mapGetters(["mobilephone", "studentId", "studentName", "password", "roleId",
        "gender", "birthday", "college", "major", "majorClass"]),
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
      this.student.mobilephone = this.mobilephone;
      this.student.studentId = this.studentId;
      this.student.studentName = this.studentName;
      this.student.password = this.password;
      this.student.gender = this.gender;
      this.student.birthday = this.birthday == null ? '' : this.student.birthday = this.getDateBySplit(this.birthday);
      this.student.college = this.college;
      this.student.major = this.major;
      this.student.majorClass = this.majorClass;
    },
  }
</script>

<style type="text/css" scoped>
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
</style>
