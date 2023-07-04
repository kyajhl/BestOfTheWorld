<template>
  <div>
    <!-- 表单搜索 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">

          <el-input
            v-model.lazy="searchForm.courseName"
            placeholder="课程名"
            @keyup.enter.native="getSelectedCourseList"
          ></el-input>

          <el-input
            v-model.lazy="searchForm.cno"
            placeholder="课程号"
            @keyup.enter.native="getSelectedCourseList"
          ></el-input>

          <el-button
            type="primary"
            round
            icon="el-icon-search"
            @click="getSelectedCourseList"
          >查询
          </el-button>

        </el-col>
        <el-col :span="4">
        </el-col>
      </el-row>
    </el-card>

    <!-- 数据展示 -->
    <el-card>
      <el-table :data="courseList" stripe style="width: 100%">

        <el-table-column label="#" width="100">
          <template slot-scope="scope">
            {{
            (searchForm.pageNo - 1) * searchForm.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>

        <el-table-column
          prop="courseName"
          label="课程名"
          width="210"
        ></el-table-column>

        <el-table-column
          prop="cno"
          label="课程号"
          width="210"
        ></el-table-column>

        <el-table-column
          prop="credit"
          label="学分"
          width="210"
        ></el-table-column>

        <el-table-column
          prop="name"
          label="授课教师"
          width="210"
        ></el-table-column>

        <el-table-column label="操作" width="230" class="floatRight">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-close"
              round
              @click="withdrawCourse(scope.row.cno, scope.row.courseName, scope.row.teacherId)"
            >退课
            </el-button>
          </template>
        </el-table-column>

      </el-table>
    </el-card>

    <!-- 分页 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="searchForm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
  </div>
</template>

<script>
  import studentManage from '@/api/studentManage'
  import {mapGetters} from 'vuex'

  export default {
    name: "CourseSelected",
    data() {
      return {
        searchForm: {
          pageNo: 1,
          pageSize: 5,
          cno: "",
          courseName: "",
          studentId: ''
        },
        courseList: [],
        total: 0,
        currentPage: 1,
      }
    },
    computed: {
      ...mapGetters(["name"]),

    },
    methods: {
      // 获取已经选择的课程列表
      getSelectedCourseList() {
        studentManage.getSelectedCourseList(this.searchForm).then(
          response => {
            this.courseList = response.data.selectedCourseList;
            this.total = response.data.total;
          },
          error => {
            this.$message.error("获取已选课程失败");
          }
        )
      },

      // 控制分页的参数
      handleSizeChange(pageSize) {
        this.searchForm.pageSize = pageSize;
        this.getSelectedCourseList();
      },
      handleCurrentChange(pageNo) {
        this.searchForm.pageNo = pageNo;
        this.getSelectedCourseList();
      },

      // 学生退课
      withdrawCourse(cno, courseName, teacherId) {
        this.$confirm(`确认退课 ${courseName} ?`, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let data = {cno, studentId: this.name, teacherId};
          studentManage.deleteSelectedCourse(data).then(
            response => {
              this.getSelectedCourseList();
              this.$message.success("退课成功");
            },
            error => {
              this.$message.error("退课失败");
            }
          )
        }).catch(() => {
          console.log("取消退课");
        });
      }
    },
    mounted() {
      // 给学生 id 赋值
      this.searchForm.studentId = this.name;
      // 获取已选择的课程列表
      this.getSelectedCourseList();
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
</style>
