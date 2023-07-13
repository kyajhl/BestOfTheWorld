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

              <el-descriptions-item label="团队号">
                {{ teamInformation.teamId }}
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
                    <el-card :body-style="{ padding: '0px' }">
                      <div style="padding: 14px;">
                        <p>姓名：{{ student.studentName }}</p>
                        <p>手机号：{{ student.mobilephone }}</p>
                        <p>职位：{{ student.position }}</p>
                      </div>
                    </el-card>
                  </el-col>
                </el-row>
              </el-descriptions-item>

              <el-descriptions-item label="小组留言">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>全部留言</span>
                  </div>
                  <div v-for="message in messageList" :key="message.mobilephone" class="text item">
                    <span>{{ message.studentName }}：</span>
                    <span>{{ message.content }}</span>
                    <span style="float: right">{{ message.messageDate }}</span>
                  </div>
                </el-card>
              </el-descriptions-item>

            </el-descriptions>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import enterpriseManage from "@/api/enterpriseManage";

  export default {
    name: "TeamInformation",
    data() {
      return {
        teamInformation: {
          teamId: '',
          teamName: '',
          projectId: '',
          projectName: '',
          studentList: [
            {
              studentName: '温荣森',
              mobilephone: '111',
              position: '产品经理'
            },
            {
              studentName: '周杰伦',
              mobilephone: '222',
              position: '项目经理'
            },
            {
              studentName: '陈少荣',
              mobilephone: '333',
              position: '技术经理'
            },
            {
              studentName: '陈少荣',
              mobilephone: '444',
              position: '技术经理'
            },
            {
              studentName: '陈少荣',
              mobilephone: '555',
              position: '技术经理'
            }
          ],
        },
        messageList: [
          {
            studentName: '温荣森',
            content: '666',
            messageDate: '2023-7-13'
          },
          {
            studentName: '周杰伦',
            content: '777',
            messageDate: '2023-7-12'
          },
          {
            studentName: '陈少荣',
            content: '888',
            messageDate: '2023-7-11'
          }
        ]
      }
    },
    props: ["teamId"],
    methods: {
      // 获取团队信息
      getTeamInformation() {

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
</style>
