<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="16" :offset="4">
        <div class="grid-content">
          <div v-if="roleId === '1'">
            <el-descriptions class="margin-top" title="欢迎来到学生管理系统" :column="2" size="medium" border>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-user"></i>
                  用户名
                </template>
                {{ name }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-s-custom"></i>
                  姓名
                </template>
                {{ realName }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-male"></i>
                  性别
                </template>
                {{ sex }}
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
                {{ telephone }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
          <div v-else>
            <el-descriptions class="margin-top" title="欢迎来到学生管理系统" :column="2" size="medium" border>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-user"></i>
                  用户名
                </template>
                {{ name }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-s-custom"></i>
                  姓名
                </template>
                {{ realName }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-male"></i>
                  性别
                </template>
                {{ sex }}
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
                  <i class="el-icon-mobile-phone"></i>
                  手机号
                </template>
                {{ telephone }}
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
      ...mapGetters(["name", "roleId", "realName", "major", "majorClass",
        "telephone", "sex", "birthday"]),
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

