<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="16" :offset="4">
        <div class="grid-content">
          <div v-if="roleId === '1'">
            <el-descriptions class="margin-top" title="欢迎来到高校实训实习管理平台" :column="2" size="medium" border>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-user"></i>
                  学号
                </template>
                {{ studentId }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-s-custom"></i>
                  姓名
                </template>
                {{ studentName }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-male"></i>
                  性别
                </template>
                {{ gender }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-date"></i>
                  生日
                </template>
                {{ birthday }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-chat-dot-square"></i>
                  专业
                </template>
                {{ major }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-connection"></i>
                  专业班级
                </template>
                {{ majorClass }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-mobile-phone"></i>
                  手机号
                </template>
                {{ mobilephone }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-location"></i>
                  学校
                </template>
                {{ college }}
              </el-descriptions-item>

            </el-descriptions>
          </div>
          <div v-else-if="roleId === '2'">
            <el-descriptions class="margin-top" title="欢迎来到高校实训实习管理平台" :column="2" size="medium" border>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-mobile-phone"></i>
                  手机号
                </template>
                {{ mobilephone }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-s-custom"></i>
                  姓名
                </template>
                {{ teacherName }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-location"></i>
                  学校
                </template>
                {{ college }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
          <div v-else>
            <el-descriptions class="margin-top" title="欢迎来到高校实训实习管理平台" :column="2" size="medium" border>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-mobile-phone"></i>
                  手机号
                </template>
                {{ mobilephone }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-s-custom"></i>
                  企业名
                </template>
                {{ enterpriseName }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-message"></i>
                  邮箱
                </template>
                {{ email }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";

  export default {
    name: "Dashboard",
    computed: {
      ...mapGetters(["studentId", "studentName", "roleId", "major", "majorClass",
        "mobilephone", "gender", "birthday", "college", "teacherName", "enterpriseName", "email"]),
    },
    mounted() {

    }
  };
</script>

<style lang="scss" scoped>
  .dashboard {
    &-container {
      margin: 30px;
    }

    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }
</style>

