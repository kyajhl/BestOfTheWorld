<template>
  <div>
    <div v-if="$route.name !== 'all_student'">

      <div>
        <el-row :gutter="20">

          <el-col :span="2">
            <div class="grid-content"></div>
          </el-col>
          <el-col :span="20">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>课程列表</span>
              </div>
              <div v-for="course in courseList" :key="course.cno" class="text">
                <router-link
                  :to="`/teacher/record_grade/all_student?teacherId=${name}&cno=${course.cno}`"
                  class="item"
                >

                  <el-row :gutter="20">
                    <el-col :span="22">
                      <div class="grid-content">
                        <el-descriptions class="margin-top"
                                         :column="4"
                                         size="medium"
                                         border
                                         style="width: 90%"
                        >

                          <el-descriptions-item>
                            <template slot="label">
                              课程名
                            </template>
                            {{ course.courseName }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              课程号
                            </template>
                            {{ course.cno }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              学分
                            </template>
                            {{ course.credit }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              人数
                            </template>
                            {{ course.selectedNumber }}
                          </el-descriptions-item>

                        </el-descriptions>
                      </div>
                    </el-col>
                    <el-col :span="2">
                      <div class="grid-content">
                        <span class="el-icon-arrow-right rightFloat"></span>
                      </div>
                    </el-col>
                  </el-row>

                </router-link>
              </div>
            </el-card>
          </el-col>
          <el-col :span="2">
            <div class="grid-content"></div>
          </el-col>

        </el-row>
      </div>

    </div>
    <!--  显示三级路由  -->
    <router-view></router-view>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import teacherManage from "@/api/teacherManage";

  export default {
    name: "RecordGrade",
    data() {
      return {
        searchForm: {
          teacherId: ''
        },
        courseList: []
      }
    },
    computed: {
      ...mapGetters(["name"]),

    },
    methods: {
      getTeachingCourseListModifyStudent() {
        teacherManage.getTeachingCourseListModifyStudent(this.searchForm).then(
          response => {
            this.courseList = response.data.teachingCourseList;
          },
          error => {
            this.$message.error("获取课程列表失败");
          }
        )
      }
    },
    mounted() {
      // 给老师 id 赋值
      this.searchForm.teacherId = this.name;
      this.getTeachingCourseListModifyStudent();

    }
  }
</script>

<style scoped type="text/css">
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
    padding: 30px 0;
    border-bottom: 1px solid #e0e9e9;
    /*background-color: skyblue;*/
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
