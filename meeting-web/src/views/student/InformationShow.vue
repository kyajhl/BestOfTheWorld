<template>
  <div>
    <!--  数据列表  -->
    <el-row :gutter="20">
      <el-col :span="8">
        <div :class="{cardClass: true}">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>留言列表</span>
              <el-button
                style="float: right; padding: 3px 0"
                type="text"
                @click="openDialog('666')"
              >添加留言
              </el-button>
            </div>
            <div v-for="(message, index) in messageList" :key="index" class="text item">
              <el-row>
                <el-col :span="4" style="font-weight: bold">
                  {{ message.studentName }}：
                </el-col>
                <el-col :span="13">
                  <div class="content">{{ message.content }}</div>
                </el-col>
                <el-col :span="5" style="float: right">
                  {{ message.messageDate }}
                </el-col>
              </el-row>
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col :span="8">
        <div :class="{cardClass: true}">
          <el-card class="box-card" style="margin-bottom: 20px">
            <div slot="header" class="clearfix">
              <span>个人日志列表</span>
            </div>
            <div class="block">
              <div class="radio" style="margin-bottom: 20px;margin-left: 30px">
                排序：
                <el-radio-group v-model="reverse">
                  <el-radio :label="true">倒序</el-radio>
                  <el-radio :label="false">正序</el-radio>
                </el-radio-group>
              </div>
              <el-timeline :reverse="reverse">

                <el-timeline-item
                  v-for="personLogs in personLogsList"
                  :key="personLogs.logDate"
                  :timestamp="personLogs.logDate"
                  placement="top"
                >

                  <div>
                    <div style="float: right;margin-top: 10px">
                      <el-button
                        @click="openDialog('777', personLogs.id)"
                        type="primary"
                        circle icon="el-icon-edit"
                        size="mini"
                        :disabled="personLogs.comment !== null"
                      ></el-button>
                      <el-button
                        @click="deletePersonLog(personLogs)"
                        type="danger"
                        circle
                        icon="el-icon-delete"
                        size="mini"
                        :disabled="personLogs.comment !== null"
                      ></el-button>
                    </div>
                    <el-collapse v-model="activeName" accordion>
                      <el-collapse-item
                        :title="personLogs.content.substring(0, 6) + '.........'"
                        :name="personLogs.logDate"
                      >
                        <div style="margin-bottom: 15px">
                          <span style="font-weight: bold">日志内容：</span>
                          <div>
                            {{ personLogs.content }}
                          </div>
                        </div>
                        <div v-if="personLogs.comment">
                          <el-row>
                            <el-col :span="15" class="content">
                              <span style="font-weight: bold">教师评语：</span>
                              <div>{{ personLogs.comment }}</div>
                            </el-col>
                            <el-col :span="6" style="margin-left: 20px">
                              <span style="font-weight: bold">分数：</span>{{ personLogs.grade }}
                            </el-col>
                          </el-row>
                        </div>
                        <div v-else>
                          <el-empty description="无数据" :image-size="50" style="height: 50px"></el-empty>
                        </div>
                      </el-collapse-item>
                    </el-collapse>
                  </div>

                </el-timeline-item>

              </el-timeline>
            </div>
          </el-card>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>团队日志列表</span>
            </div>
            <div class="block">
              <div class="radio" style="margin-bottom: 20px;margin-left: 30px">
                排序：
                <el-radio-group v-model="reverse1">
                  <el-radio :label="true">倒序</el-radio>
                  <el-radio :label="false">正序</el-radio>
                </el-radio-group>
              </div>
              <div>
                <el-timeline :reverse="reverse1">
                  <el-timeline-item
                    v-for="teamLogs in teamLogsList"
                    :key="teamLogs.logDate"
                    :timestamp="teamLogs.logDate"
                    placement="top"
                  >

                    <div>
                      <div style="float: right;margin-top: 10px">
                        <el-button
                          @click="openDialog('888', teamLogs.id)"
                          type="primary"
                          circle icon="el-icon-edit"
                          size="mini"
                          :disabled="teamLogs.comment !== null || teamLogs.position !== '项目经理'"
                        ></el-button>
                        <el-button
                          @click="deleteTeamLog(teamLogs)"
                          type="danger"
                          circle
                          icon="el-icon-delete"
                          size="mini"
                          :disabled="teamLogs.comment !== null || teamLogs.position !== '项目经理'"
                        ></el-button>
                      </div>
                      <el-collapse v-model="activeName" accordion>
                        <el-collapse-item
                          :title="teamLogs.content.substring(0, 6) + '.........'"
                          :name="teamLogs.logDate"
                        >
                          <div style="margin-bottom: 15px">
                            <span style="font-weight: bold">日志内容：</span>
                            <div>
                              {{ teamLogs.content }}
                            </div>
                          </div>
                          <div v-if="teamLogs.comment">
                            <el-row>
                              <el-col :span="15" class="content">
                                <span style="font-weight: bold">教师评语：</span>
                                <div>{{ teamLogs.comment }}</div>
                              </el-col>
                              <el-col :span="6" style="margin-left: 20px">
                                <span style="font-weight: bold">分数：</span>{{ teamLogs.grade }}
                              </el-col>
                            </el-row>
                          </div>
                          <div v-else>
                            <el-empty description="无数据" :image-size="50" style="height: 50px"></el-empty>
                          </div>
                        </el-collapse-item>
                      </el-collapse>
                    </div>

                  </el-timeline-item>

                </el-timeline>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col :span="8">
        <div :class="{cardClass: true}">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>实训总结列表</span>
            </div>
            <div v-for="summary in summaryList" :key="summary.id" class="text item">
              <div>
                <div style="float: right;margin-top: 10px">
                  <el-button
                    @click="openDialog('999', summary.id)"
                    type="primary"
                    circle icon="el-icon-edit"
                    size="mini"
                  ></el-button>
                </div>
              </div>
              <el-collapse v-model="activeName2" accordion>
                <el-collapse-item :title="summary.content.substring(0, 6) + '.........'" :name="summary.id">
                  <div>
                    {{ summary.content }}
                  </div>
                </el-collapse-item>
              </el-collapse>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>

    <!-- 留言、个人日志、团队日志、总结表单   -->
    <el-dialog
      @close="clearForm"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form
        :model="messageForm"
        :rules="rules"
        ref="messageFormRef"
        v-if="this.id === '666'"
      >
        <el-form-item
          prop="content"
          label="留言"
          :label-width="formLabelWidth"
        >
          <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入留言"
            v-model="messageForm.content">
          </el-input>
        </el-form-item>
      </el-form>

      <el-form
        :model="personLogForm"
        :rules="rules"
        ref="personLogFormRef"
        v-if="this.id === '777'"
      >
        <el-form-item
          prop="personLogContent"
          label="个人日志"
          :label-width="formLabelWidth"
        >
          <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入个人日志"
            v-model="personLogForm.personLogContent">
          </el-input>
        </el-form-item>
      </el-form>

      <el-form
        :model="teamLogForm"
        :rules="rules"
        ref="teamLogFormRef"
        v-if="this.id === '888'"
      >
        <el-form-item
          prop="teamLogContent"
          label="团队日志"
          :label-width="formLabelWidth"
        >
          <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入团队日志"
            v-model="teamLogForm.teamLogContent">
          </el-input>
        </el-form-item>
      </el-form>

      <el-form
        :model="summaryForm"
        :rules="rules"
        ref="summaryFormRef"
        v-if="this.id === '999'"
      >
        <el-form-item
          prop="summary"
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
  import {mapGetters} from "vuex";

  export default {
    name: "InformationShow",
    data() {
      return {
        id: '',
        studentId: '',
        teamId: '',
        activeName: '0',
        activeName1: '0',
        activeName2: '0',
        reverse: false,
        reverse1: false,
        dialogFormVisible: false,
        formLabelWidth: '100px',
        title: '',
        messageList: [],
        messageForm: {
          messageDate: '',
          content: '',
          mobilephone: ''
        },
        personLogsList: [],
        personLogForm: {
          personLogContent: '',
          studentId: ''
        },
        teamLogsList: [],
        teamLogForm: {
          teamLogContent: '',
          teamId: ''
        },
        summaryList: [
          {
            id: 1,
            content: '666666666666',
          },
          {
            id: 2,
            content: '666666666666',
          },
          {
            id: 3,
            content: '666666666666',
          }
        ],
        summaryForm: {},
        rules: {
          content: [{required: true, message: '请填写留言', trigger: 'blur'}],
          personLogContent: [{required: true, message: '请填写个人日志', trigger: 'blur'}],
          teamLogContent: [{required: true, message: '请填写团队日志', trigger: 'blur'}],
          summary: [{required: true, message: '请填写实训总结', trigger: 'blur'}],
        },
      }
    },
    computed: {
      ...mapGetters(["mobilephone"])
    },
    methods: {
      // 获取个人日志列表
      getPersonLogsList() {
        studentManage.getPersonLogsList(this.mobilephone).then(
          response => {
            this.personLogsList = response.data.personLogsList;
            this.personLogsList = this.personLogsList.map(personLogs => {
              this.studentId = personLogs.studentId;
              let dateInfo = personLogs.logDate;
              personLogs.logDate = dateInfo[0] + '-' + dateInfo[1] +
                '-' + dateInfo[2] + ' ' +
                dateInfo[3] + ':' + dateInfo[4] + ':' + dateInfo[5];
              return personLogs;
            });
            this.$message.success(response.msg);
          },
          error => {
            this.$message.error("获取个人日志失败");
          }
        )
      },
      // 更新个人日志
      updatePersonLog() {
        let content = this.personLogForm.personLogContent;
        let studentId = this.personLogForm.studentId;
        let id = this.personLogForm.id;
        this.personLogForm = {
          content,
          studentId,
          id
        };
        studentManage.updatePersonLog(this.personLogForm).then(
          response => {
            this.$message.success(response.msg);
            this.getPersonLogsList();
          },
          error => {
            this.$message.error("更新个人日志失败");
          }
        )
      },
      // 删除个人日志
      deletePersonLog(personLog) {
        let name = personLog.content.substring(0, 6) + '.........';
        this.$confirm(`确认删除 ${name} ?`, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          let id = personLog.id;
          personLog = {
            id
          };
          studentManage.deletePersonLog(personLog).then(
            response => {
              this.$message.success(response.msg);
              this.getPersonLogsList();
            },
            error => {
              this.$message.error("删除个人日志失败");
            }
          )
        });

      },
      // 获取团队日志列表
      getTeamLogsList() {
        studentManage.getIdByMobilePhone(this.mobilephone).then(
          response => {
            this.studentId = response.data.id;
            studentManage.getTeamLogsList(this.studentId).then(
              response => {
                this.teamLogsList = response.data.teamLogsList;
                this.teamLogsList = this.teamLogsList.map(teamLog => {
                  // 根据 teamId 查询对应的职位，并存到 teamLogsList 里面的 position
                  studentManage.getPositionByTeamId(teamLog.teamId, this.studentId).then(
                    response => {
                      let position = response.data.position;
                      // 如果没有 position 这个属性，就添加一个
                      if (!teamLog.hasOwnProperty('position')) {
                        this.$set(teamLog, 'position', '');
                      }
                      teamLog['position'] = position;
                    },
                    error => {
                      this.$message.error("获取对应职位失败");
                    }
                  );
                  // 赋值别的属性
                  let dateInfo = teamLog.logDate;
                  teamLog.logDate = dateInfo[0] + '-' + dateInfo[1] +
                    '-' + dateInfo[2] + ' ' +
                    dateInfo[3] + ':' + dateInfo[4] + ':' + dateInfo[5];
                  return teamLog;
                });
                this.$message.success(response.msg);
              },
              error => {
                this.$message.error("获取团队日志失败");
              }
            )
          },
          error => {
            this.$message.error("获取学生的id失败");
          }
        );

      },
      // 更新团队日志
      updateTeamLog() {
        let content = this.teamLogForm.teamLogContent;
        let teamId = this.teamLogForm.teamId;
        let id = this.teamLogForm.id;
        this.teamLogForm = {
          content,
          teamId,
          id
        };
        console.log(this.teamLogForm);
        studentManage.updateTeamLog(this.teamLogForm).then(
          response => {
            this.$message.success(response.msg);
            this.getTeamLogsList();
          },
          error => {
            this.$message.error("更新团队日志失败");
          }
        )
      },
      // 删除团队日志
      deleteTeamLog(teamLog) {
        let name = teamLog.content.substring(0, 6) + '.........';
        this.$confirm(`确认删除 ${name} ?`, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          let id = teamLog.id;
          teamLog = {
            id
          };
          studentManage.deleteTeamLog(teamLog).then(
            response => {
              this.$message.success(response.msg);
              this.getTeamLogsList();
            },
            error => {
              this.$message.error("删除团队日志失败");
            }
          )
        });
      },
      // 获取实训总结
      getSummaryList() {

      },
      // 更新实训总结
      updateSummary() {

      },
      // 打开表单
      openDialog(id, searchId) {
        this.dialogFormVisible = true;
        // 666 --- 留言  777 --- 个人日志  888 --- 团队日志  999 --- 实训总结
        if (id === '666') {
          console.log(666);
          this.id = '666';
          this.title = '添加留言';
          this.messageForm = {};
        } else if (id === '777') {
          console.log(777);
          this.id = '777';
          this.title = '修改个人日志';
          this.personLogForm = {
            studentId: this.studentId,
            id: searchId
          };
          console.log(this.personLogForm)
        } else if (id === '888') {
          console.log(888);
          this.id = '888';
          this.title = '修改团队日志';
          this.teamLogForm = {
            teamId: this.teamLogsList[0].teamId,
            id: searchId
          }
        } else {
          console.log(999);
          this.id = '999';
          this.title = '修改实训总结';

        }
      },
      // 获取留言列表
      getMessageList() {
        studentManage.getMessageList().then(
          response => {
            this.messageList = response.data.messageList;
            this.messageList = this.messageList.map(message => {
              message.messageDate = message.messageDate.join('-');
              return message;
            });
            this.$message.success(response.msg)
          },
          error => {
            this.$message.error("获取留言列表失败");
          }
        )
      },
      // 保存信息
      saveInformation() {
        if (this.id === '666') {
          this.$refs['messageFormRef'].validate((valid) => {
            if (valid) {
              this.messageForm.mobilephone = this.mobilephone;
              this.messageForm.messageDate = new Date();
              studentManage.addMessage(this.messageForm).then(
                response => {
                  this.$message.success(response.msg);
                  this.dialogFormVisible = false;
                  this.getMessageList();
                },
                error => {
                  this.$message.error("添加留言失败");
                  this.dialogFormVisible = false;
                }
              )
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        } else if (this.id === '777') {
          this.$refs['personLogFormRef'].validate((valid) => {
            if (valid) {
              this.updatePersonLog();
              this.dialogFormVisible = false;
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        } else if (this.id === '888') {
          this.$refs['teamLogFormRef'].validate((valid) => {
            if (valid) {
              this.updateTeamLog();
              this.dialogFormVisible = false;
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        } else {
          this.$refs['summaryFormRef'].validate((valid) => {
            if (valid) {
              this.updateSummary();
              this.dialogFormVisible = false;
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        }
      },
      // 清除表单的信息
      clearForm() {
        // 清除表单验证提示信息
        if (this.id === '666') {
          this.$refs.messageFormRef.clearValidate();
        } else if (this.id === '777') {
          this.$refs.personLogFormRef.clearValidate();
        } else if (this.id === '888') {
          this.$refs.teamLogFormRef.clearValidate();
        } else {
          this.$refs.summaryFormRef.clearValidate();
        }
      }
    },
    mounted() {
      // 获取留言
      this.getMessageList();
      // 获取个人日志
      this.getPersonLogsList();
      // 获取团队日志
      this.getTeamLogsList();
    }
  }
</script>

<style scoped>
  .content {
    word-wrap: break-word;
    word-break: break-all;
    /*overflow: hidden;!*这个参数根据需求来决定要不要*!*/
  }

  .cardClass {
    height: 600px;
    overflow-x: hidden;
    overflow-y: scroll;
  }

  .cardClass::-webkit-scrollbar {
    display: none;
  }

  .text {
    font-size: 14px;
  }

  .item {
    /*margin-bottom: 18px;*/
    padding: 10px 0;
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
    width: 420px;
  }
</style>
