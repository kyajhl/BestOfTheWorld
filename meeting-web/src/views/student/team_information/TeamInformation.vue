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

            </el-descriptions>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import studentManage from "@/api/studentManage";

  export default {
    name: "TeamInformation",
    data() {
      return {
        teamInformation: {
          teamId: '',
          teamName: '',
          projectId: '',
          projectName: '',
          studentList: [],
        },
        messageList: []
      }
    },
    props: ["teamId"],
    methods: {
      // 获取团队信息
      getTeamInformation() {
        studentManage.getTeamInformation(this.teamId).then(
          response => {
            this.teamInformation.teamId = this.teamId;
            this.teamInformation.teamName = response.data.teamName;
            this.teamInformation.projectId = response.data.projectId;
            this.teamInformation.projectName = response.data.projectName;
            this.$message.success(response.msg);
          },
          error => {
            this.$message.error("获取团队信息失败");
          }
        )
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
