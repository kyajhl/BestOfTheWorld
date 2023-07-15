<template>
  <div>
    <div class="dashboard-container">
      <el-row :gutter="20">
        <el-col :span="16" :offset="4">
          <div class="grid-content">
            <el-descriptions
              title="学生信息"
              direction="vertical"
              :column="1" border
              size="medium"
              style="margin-top: 20px"
            >
              <el-descriptions-item
                label="个人日志"
              >
                <div class="block cardClass">
                  <div class="radio" style="margin-left: 40px;margin-bottom: 20px;">
                    排序：
                    <el-radio-group v-model="reverse">
                      <el-radio :label="true">倒序</el-radio>
                      <el-radio :label="false">正序</el-radio>
                    </el-radio-group>
                  </div>
                  <el-timeline :reverse="reverse">
                    <el-timeline-item
                      v-for="studentLog in studentLogs"
                      :key="studentLog.logDate"
                      :timestamp="studentLog.logDate"
                      placement="top"
                    >
                      <div>
                        <div style="float: right;margin-top: 10px;margin-right: 20px">
                          <el-button
                            @click="openDialog('666', studentLog)"
                            type="primary"
                            round
                            size="mini"
                            :disabled="studentLog.comment !== null && studentLog.comment.length !== 0"
                          >
                            留言指导评分
                          </el-button>
                        </div>
                        <div>
                          <el-collapse v-model="activeName" accordion>
                            <el-collapse-item
                              :title="studentLog.content.substring(0, 6) + '.........'"
                              :name="studentLog.logDate"
                            >
                              <div style="margin-bottom: 15px">
                                <span style="font-weight: bold">日志内容：</span>
                                <div>
                                  {{ studentLog.content }}
                                </div>
                              </div>
                              <div v-if="studentLog.comment">
                                <el-row>
                                  <el-col :span="15" class="content">
                                    <span style="font-weight: bold">教师评语：</span>
                                    <div>{{ studentLog.comment }}</div>
                                  </el-col>
                                  <el-col :span="6" style="margin-left: 20px">
                                    <span style="font-weight: bold">分数：</span>{{ studentLog.grade }}
                                  </el-col>
                                </el-row>
                              </div>
                              <div v-else>
                                <el-empty description="无数据" :image-size="50" style="height: 50px"></el-empty>
                              </div>
                            </el-collapse-item>
                          </el-collapse>
                        </div>
                      </div>
                    </el-timeline-item>
                  </el-timeline>
                </div>
              </el-descriptions-item>

              <el-descriptions-item
                label="团队日志"
              >
                <div class="block cardClass">
                  <div class="radio" style="margin-left: 40px;margin-bottom: 20px;">
                    排序：
                    <el-radio-group v-model="reverse1">
                      <el-radio :label="true">倒序</el-radio>
                      <el-radio :label="false">正序</el-radio>
                    </el-radio-group>
                  </div>
                  <el-timeline :reverse="reverse1">
                    <el-timeline-item
                      v-for="teamLog in teamLogs"
                      :key="teamLog.logDate"
                      :timestamp="teamLog.logDate"
                      placement="top"
                    >
                      <div>
                        <div style="float: right;margin-top: 10px;margin-right: 20px">
                          <el-button
                            @click="openDialog('777', teamLog)"
                            type="primary"
                            round
                            size="mini"
                            :disabled="teamLog.comment !== null && teamLog.comment.length !== 0"
                          >
                            留言指导评分
                          </el-button>
                        </div>
                        <div>
                          <el-collapse v-model="activeName1" accordion>
                            <el-collapse-item
                              :title="teamLog.content.substring(0, 6) + '.........'"
                              :name="teamLog.logDate"
                            >
                              <div style="margin-bottom: 15px">
                                <span style="font-weight: bold">日志内容：</span>
                                <div>
                                  {{ teamLog.content }}
                                </div>
                              </div>
                              <div v-if="teamLog.comment">
                                <el-row>
                                  <el-col :span="15" class="content">
                                    <span style="font-weight: bold">教师评语：</span>
                                    <div>{{ teamLog.comment }}</div>
                                  </el-col>
                                  <el-col :span="6" style="margin-left: 20px">
                                    <span style="font-weight: bold">分数：</span>{{ teamLog.grade }}
                                  </el-col>
                                </el-row>
                              </div>
                              <div v-else>
                                <el-empty description="无数据" :image-size="50" style="height: 50px"></el-empty>
                              </div>
                            </el-collapse-item>
                          </el-collapse>
                        </div>
                      </div>
                    </el-timeline-item>
                  </el-timeline>
                </div>
              </el-descriptions-item>

              <el-descriptions-item label="个人实训总结">
                <div class="cardClass">
                  <el-collapse v-model="activeName2" accordion>
                    <el-collapse-item
                      :title="summary.content.substring(0, 6) + '.........'"
                      :name="summary.id"
                      v-if="summary.content.length"
                    >
                      <div style="margin-bottom: 15px">
                        <span style="font-weight: bold">总结内容：</span>
                        <div>
                          {{ summary.content }}
                        </div>
                      </div>
                    </el-collapse-item>
                    <el-collapse-item v-else>
                      <el-empty description="无数据" :image-size="50" style="height: 50px"></el-empty>
                    </el-collapse-item>
                  </el-collapse>
                </div>
              </el-descriptions-item>

            </el-descriptions>
          </div>
        </el-col>
      </el-row>
    </div>

    <!--  留言打分表单  -->
    <el-dialog
      @close="clearForm"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form
        :model="studentLogsForm"
        :rules="rules"
        ref="studentLogFormRef"
        v-if="this.id === '666'"
      >
        <el-form-item
          prop="studentMessage"
          label="留言"
          :label-width="formLabelWidth"
        >
          <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入留言"
            v-model="studentLogsForm.studentMessage">
          </el-input>
        </el-form-item>
        <el-form-item
          prop="studentGrade"
          label="分数"
          :label-width="formLabelWidth"
        >
          <el-input
            type="text"
            placeholder="请输入分数"
            v-model.number="studentLogsForm.studentGrade">
          </el-input>
        </el-form-item>
      </el-form>

      <el-form
        :model="teamLogsForm"
        :rules="rules"
        ref="teamLogFormRef"
        v-if="this.id === '777'"
      >
        <el-form-item
          prop="teamMessage"
          label="留言"
          :label-width="formLabelWidth"
        >
          <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入留言"
            v-model="teamLogsForm.teamMessage">
          </el-input>
        </el-form-item>
        <el-form-item
          prop="teamGrade"
          label="分数"
          :label-width="formLabelWidth"
        >
          <el-input
            type="text"
            placeholder="请输入分数"
            v-model.number="teamLogsForm.teamGrade">
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
  import teacherManage from "@/api/teacherManage";
  import studentManage from "@/api/studentManage";

  export default {
    name: "StudentInformation",
    data() {
      return {
        studentId: '',
        id: '',
        dialogFormVisible: false,
        formLabelWidth: '100px',
        title: '',
        activeName: '0',
        activeName1: '0',
        activeName2: '0',
        reverse: false,
        reverse1: false,
        studentLogs: [],
        studentLogsForm: {
          studentMessage: '',
          studentGrade: '',
          id: ''
        },
        teamLogs: [],
        teamLogsForm: {
          teamMessage: '',
          teamGrade: ''
        },
        summary: {
          content: ''
        },
        rules: {
          studentMessage: [{required: true, message: '请填写学生日志留言', trigger: 'blur'}],
          studentGrade: [{required: true, message: '请填写学生日志分数', trigger: 'blur'}],
          teamMessage: [{required: true, message: '请填写团队日志留言', trigger: 'blur'}],
          teamGrade: [{required: true, message: '请填写团队日志分数', trigger: 'blur'}],
        }
      }
    },
    props: ["mobilephone"],
    methods: {
      // 获取学生个人日志
      getStudentLogs() {
        teacherManage.getStudentLogs(this.mobilephone).then(
          response => {
            this.studentLogs = response.data.personLogsList;
            this.studentLogs = this.studentLogs.map(studentLog => {
              this.studentId = studentLog.studentId;
              let dateInfo = studentLog.logDate;
              studentLog.logDate = dateInfo[0] + '-' + dateInfo[1] +
                '-' + dateInfo[2] + ' ' +
                dateInfo[3] + ':' + dateInfo[4] + ':' + dateInfo[5];
              return studentLog;
            });
            this.$message.success(response.msg);
          },
          error => {
            this.$message.error("获取学生个人日志失败");
          }
        )
      },
      // 获取学生团队日志
      getTeamLogs() {
        studentManage.getIdByMobilePhone(this.mobilephone).then(
          response => {
            this.studentId = response.data.id;
            teacherManage.getTeamLogs(this.studentId).then(
              response => {
                this.teamLogs = response.data.teamLogsList;
                this.teamLogs = this.teamLogs.map(teamLog => {
                  let dateInfo = teamLog.logDate;
                  teamLog.logDate = dateInfo[0] + '-' + dateInfo[1] +
                    '-' + dateInfo[2] + ' ' +
                    dateInfo[3] + ':' + dateInfo[4] + ':' + dateInfo[5];
                  return teamLog;
                });
                this.$message.success(response.msg);
              },
              error => {
                this.$message.error("获取团队个人日志失败");
              }
            )
          }
        );

      },
      // 获取实训总结
      getSummaryList() {
        studentManage.getSummaryList(this.mobilephone).then(
          response => {
            this.summary = response.data;
            this.$message.success(response.msg);
          },
          error => {
            this.$message.error("获取实训总结失败");
          }
        )
      },
      // 教师留言和打分
      saveInformation() {
        if (this.id === '666') {
          this.studentLogsForm.comment = this.studentLogsForm.studentMessage;
          this.studentLogsForm.grade = this.studentLogsForm.studentGrade;
          this.$refs['studentLogFormRef'].validate((valid) => {
            if (valid) {
              teacherManage.updateStudentLog(this.studentLogsForm).then(
                response => {
                  this.$message.success("个人日志留言打分成功");
                  this.getStudentLogs();
                  this.dialogFormVisible = false;
                },
                error => {
                  this.$message.error("个人日志留言打分失败");
                }
              )
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        } else {
          this.teamLogsForm.comment = this.teamLogsForm.teamMessage;
          this.teamLogsForm.grade = this.teamLogsForm.teamGrade;
          console.log(this.teamLogsForm);
          this.$refs['teamLogFormRef'].validate((valid) => {
            if (valid) {
              teacherManage.updateTeamLog(this.teamLogsForm).then(
                response => {
                  this.$message.success("团队日志留言打分成功");
                  this.getTeamLogs();
                  this.dialogFormVisible = false;
                },
                error => {
                  this.$message.error("团队日志留言打分失败");
                }
              )
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        }
      },
      openDialog(id, log) {
        // 666 --- 个人日志  777 --- 团队日志
        this.dialogFormVisible = true;
        if (id === '666') {
          this.title = '指导个人日志';
          this.id = '666';
          this.studentLogsForm = {
            id: log.id,
            content: log.content,
            logDate: this.timeStr2date(log.logDate),
            studentId: log.studentId
          }
        } else {
          this.title = '指导团队日志';
          this.id = '777';
          this.teamLogsForm = {
            id: log.id,
            content: log.content,
            logDate: this.timeStr2date(log.logDate),
            teamId: log.teamId
          }
        }
      },
      // 时间字符串转换为时间日期
      timeStr2date(timeStr) {
        timeStr = timeStr.replace(/-/g,"/");
        console.log(timeStr);
        return new Date(timeStr);
      },
      // 清除表单的信息
      clearForm() {
        // 清除表单验证提示信息
        if (this.id === '666') {
          this.$refs.studentLogFormRef.clearValidate();
        } else {
          this.$refs.teamLogFormRef.clearValidate();
        }
      }
    },
    mounted() {
      this.getStudentLogs();
      this.getTeamLogs();
      this.getSummaryList();
    }
  }
</script>

<style scoped>
  .cardClass {
    height: 400px;
    overflow-x: hidden;
    overflow-y: scroll;
  }

  .content {
    word-wrap: break-word;
    word-break: break-all;
    /*overflow: hidden;!*这个参数根据需求来决定要不要*!*/
  }
</style>
