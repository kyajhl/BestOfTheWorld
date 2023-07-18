<template>
  <div>
    <div class="dashboard-container">
      <el-row :gutter="20">
        <el-col :span="16" :offset="4">
          <div class="grid-content">
            <el-descriptions
              title="团队信息"
              direction="vertical"
              :column="1" border
              size="medium"
            >
              <el-descriptions-item label="项目号">
                {{ teamInformation.projectId }}
              </el-descriptions-item>

              <el-descriptions-item label="项目名">
                {{ teamInformation.projectName }}
              </el-descriptions-item>

              <el-descriptions-item label="团队名">
                {{ teamInformation.teamName }}
              </el-descriptions-item>

              <el-descriptions-item label="小组成员">
                <el-row>
                  <el-col
                    :span="8"
                    v-for="student in teamInformation.studentList"
                    :key="student.mobilephone"
                  >
                    <el-card
                      :body-style="{ padding: '0px' }"
                      style="margin-right: 10px"
                    >
                      <div style="padding: 14px;">
                        <p>姓名：{{ student.studentName }}</p>
                        <p>手机号：{{ student.mobilephone }}</p>
                        <p>职位：{{ student.position }}</p>
                      </div>
                    </el-card>
                  </el-col>
                </el-row>
              </el-descriptions-item>

              <el-descriptions-item label="操作">

                <el-row>
                  <el-col :span="12">
                    <el-button
                      type="primary"
                      round
                      style="margin-right: 10px"
                      @click="openDialog('777')"
                      :disabled="this.position !== '项目经理'"
                    >
                      发布团队日志
                    </el-button>
                  </el-col>
                  <el-col :span="12">
                    <el-button
                      type="primary"
                      round
                      @click="openDialog('666')"
                    >
                      提交实训总结
                    </el-button>
                  </el-col>
                </el-row>

              </el-descriptions-item>

            </el-descriptions>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 日志和实训总结表单 -->
    <el-dialog
      @close="clearForm"
      :title="title"
      :visible.sync="dialogFormVisible"
    >

      <el-form
        :model="logsForm"
        :rules="rules"
        ref="logsFormRef"
        v-if="this.id === '777'"
      >
        <el-form-item
          prop="logsContent"
          label="日志"
          :label-width="formLabelWidth"
        >
          <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入日志"
            v-model="logsForm.logsContent">
          </el-input>
        </el-form-item>
      </el-form>

      <el-form
        :model="summaryForm"
        :rules="rules"
        ref="summaryFormRef"
        v-if="this.id === '666'"
      >
        <el-form-item
          prop="summaryContent"
          label="实训总结"
          :label-width="formLabelWidth"
        >
          <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入实训总结"
            v-model="summaryForm.content">
          </el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveInformation">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import studentManage from "@/api/studentManage";
  import {mapGetters} from 'vuex';

  export default {
    name: "TeamInformation",
    data() {
      return {
        id: '',
        formLabelWidth: '100px',
        dialogFormVisible: false,
        teamInformation: {
          teamId: '',
          teamName: '',
          projectId: '',
          projectName: '',
          studentList: [],
        },
        messageList: [],
        logsForm: {
          logsContent: '',
          teamId: ''
        },
        summaryForm: {
          content: '',
          studentId: '',
          teamId: ''
        },
        title: '',
        rules: {
          logsContent: [
            {required: true, message: '请填写日志', trigger: 'blur'}
          ],
          summaryContent: [
            {required: true, message: '请填写实训总结', trigger: 'blur'}
          ]
        },
        position: ''
      }
    },
    props: ["teamId"],
    computed: {
      ...mapGetters(["mobilephone"]),
    },
    methods: {
      // 打开表单
      openDialog(id) {
        if (id === '666') {
          // 实训总结
          this.id = '666';
          this.title = '提交实训总结';
          this.dialogFormVisible = true;
          this.summaryForm = {
            studentId: this.mobilephone,
            teamId: this.teamId
          };
        } else {
          // 日志
          this.id = '777';
          this.title = '发布日志';
          this.dialogFormVisible = true;
          this.logsForm = {
            teamId: this.teamId
          };
        }
      },
      // 保存信息
      saveInformation() {
        if (this.id === '666') {
          this.$refs['summaryFormRef'].validate((valid) => {
            if (valid) {
              // 保存总结
              studentManage.addSummary(this.summaryForm).then(
                response => {
                  this.$message.success(response.msg);
                  this.dialogFormVisible = false;
                },
                error => {
                  this.$message.error("添加实训总结失败");
                }
              )
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        } else {
          this.$refs['logsFormRef'].validate((valid) => {
            if (valid) {
              // 保存日志
              studentManage.addTeamLogs(this.logsForm).then(
                response => {
                  this.$message.success(response.msg);
                  this.dialogFormVisible = false;
                },
                error => {
                  this.$message.error("添加日志失败");
                }
              )
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        }
      },
      // 获取团队信息
      getTeamInformation() {
        studentManage.getTeamInformation(this.teamId).then(
          response => {
            this.teamInformation.teamId = this.teamId;
            this.teamInformation.teamName = response.data.teamName;
            this.teamInformation.projectId = response.data.projectId;
            this.teamInformation.projectName = response.data.projectName;
            this.teamInformation.studentList = response.data.studentList;
            this.teamInformation.studentList.forEach(student => {
              if (student.mobilephone === this.mobilephone) {
                this.position = student.position;
              }
            });
            this.$message.success(response.msg);
          },
          error => {
            this.$message.error("获取团队信息失败");
          }
        )
      },
      // 清除表单的信息
      clearForm() {
        // 清除表单验证提示信息
        if (this.id === '666') {
          this.$refs.summaryFormRef.clearValidate();
        } else {
          this.$refs.logsFormRef.clearValidate();
        }
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
      // 获取团队信息
      this.getTeamInformation();
    }
  }
</script>

<style scoped>
  .dashboard-container {
    margin-top: 30px;
  }

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 900px;
  }
</style>
