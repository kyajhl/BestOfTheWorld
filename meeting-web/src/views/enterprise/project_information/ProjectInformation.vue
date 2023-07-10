<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="16" :offset="4">
        <div class="grid-content">
          <el-descriptions
            title="项目信息"
            direction="vertical"
            :column="1" border
            size="medium"
          >
            <el-descriptions-item label="项目号">
              {{ projectInformation.projectId }}
            </el-descriptions-item>
            <el-descriptions-item label="项目名">
              {{ projectInformation.projectName }}
            </el-descriptions-item>
            <el-descriptions-item label="开始时间">
              {{ projectInformation.beginDate }}
            </el-descriptions-item>
            <el-descriptions-item label="结束时间">
              {{ projectInformation.endDate }}
            </el-descriptions-item>
            <el-descriptions-item label="人数容量">
              {{ projectInformation.volume }}
            </el-descriptions-item>
            <el-descriptions-item label="验收标准">
              {{ projectInformation.acceptanceCriteria }}
            </el-descriptions-item>
            <el-descriptions-item label="项目简介">
              {{ projectInformation.content }}
            </el-descriptions-item>
          </el-descriptions>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import enterpriseManage from "@/api/enterpriseManage";

  export default {
    name: "ProjectInformation",
    data() {
      return {
        projectInformation: {
          projectId: '',
          projectName: '',
          beginDate: '',
          endDate: '',
          content: '',
          volume: '',
          acceptanceCriteria: ''
        }
      }
    },
    props: ["projectId"],
    methods: {
      // 获取项目信息
      getProjectInformation() {
        enterpriseManage.getProject(this.projectId).then(
          response => {
            this.$message.success(response.msg);
            this.projectInformation = response.data;
            this.projectInformation.beginDate = this.projectInformation.beginDate.join('-');
            this.projectInformation.endDate = this.projectInformation.endDate.join('-');
          },
          error => {
            this.$message.error("获取项目信息失败");
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
      // 获取项目信息
      this.getProjectInformation();
    }
  }
</script>

<style scoped>
  .dashboard-container {
    margin-top: 30px;
  }
</style>
