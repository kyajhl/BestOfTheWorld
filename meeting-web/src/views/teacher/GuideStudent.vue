<template>
  <div>
    <div v-if="$route.name !== 'student_information'">
      <!-- 表单搜索 -->
      <el-card id="search">
        <el-row>
          <el-col :span="20">

            <el-input
              v-model.lazy="searchForm.studentName"
              placeholder="学生名"
              @keyup.enter.native="getStudentList"
            ></el-input>

            <el-button
              type="primary"
              round
              icon="el-icon-search"
              @click="getStudentList"
            >查询
            </el-button>

          </el-col>
        </el-row>
      </el-card>

      <!-- 数据列表 -->
      <div>
        <el-row :gutter="20">

          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>学生列表</span>
              </div>
              <div v-for="student in studentList" :key="student.id" class="text">
                <el-row :gutter="20">
                  <el-col :span="24">
                    <router-link
                      :to="`/teacher/guide_student/student_information?mobilephone=${student.mobilephone}`"
                      class="item"
                    >
                      <div class="grid-content">
                        <el-descriptions class="margin-top"
                                         :column="5"
                                         size="medium"
                                         border
                                         style="width: 100%"
                        >

                          <el-descriptions-item>
                            <template slot="label">
                              学生名
                            </template>
                            {{ student.studentName }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              手机号
                            </template>
                            {{ student.mobilephone }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              性别
                            </template>
                            {{ student.gender }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              所在大学
                            </template>
                            {{ student.college }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              团队人数
                            </template>
                            {{ student.teamNumber }}
                          </el-descriptions-item>

                        </el-descriptions>
                      </div>
                    </router-link>
                  </el-col>
                </el-row>
              </div>
            </el-card>
          </el-col>

        </el-row>
      </div>

      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="searchForm.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>

    </div>
    <!--  显示三级路由  -->
    <router-view></router-view>
  </div>
</template>

<script>
  import teacherManage from "@/api/teacherManage";

  export default {
    name: "GuideStudent",
    data() {
      return {
        searchForm: {
          pageNo: 1,
          pageSize: 5,
          studentName: ''
        },
        studentList: [],
        total: 0,
        currentPage: 1,
      }
    },
    methods: {
      getStudentList() {
        teacherManage.getStudentList(this.searchForm).then(
          response => {
            this.studentList = response.data.studentList;
            this.total = response.data.total;
            this.$message.success(response.msg);
          },
          error => {
            this.$message.error("获取学生列表失败")
          }
        )
      },
      // 分页数据
      handleSizeChange(pageSize) {
        this.searchForm.pageSize = pageSize;
        this.getStudentList();
      },
      handleCurrentChange(pageNo) {
        this.searchForm.pageNo = pageNo;
        this.getStudentList();
      }
    },
    mounted() {
      this.getStudentList();
    }
  }
</script>

<style scoped>
  #search .el-input {
    width: 200px;
    margin-right: 10px;
  }

  .floatRight {
    float: right;
  }

  .el-dialog .el-input {
    width: 85%;
  }

  .rightFloat {
    float: right;
    display: none;
    margin-top: 18px;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

  .text {
    font-size: 14px;
  }

  .item {
    display: inline-block;
    width: 100%;
    padding: 15px 0;
    border-bottom: 1px solid #e0e9e9;
  }

  .operation {
    padding: 15px 0;
  }

  .item:hover {
    background-color: #f9f9f9;
    cursor: pointer;
  }

  .item:hover .rightFloat {
    display: block;
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
    width: 100%;
  }
</style>
