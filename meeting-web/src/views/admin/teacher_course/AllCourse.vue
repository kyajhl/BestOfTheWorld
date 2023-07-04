<template>
  <div>
    <!-- 表单搜索 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">

          <el-input
            v-model.lazy="searchForm.courseName"
            placeholder="课程名"
            @keyup.enter.native="getCourseListDistributeTeacher"
          ></el-input>

          <el-input
            v-model.lazy="searchForm.cno"
            placeholder="课程号"
            @keyup.enter.native="getCourseListDistributeTeacher"
          ></el-input>

          <el-button
            type="primary"
            round
            icon="el-icon-search"
            @click="getCourseListDistributeTeacher"
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

        <el-table-column label="#" width="80">
          <template slot-scope="scope">
            {{
            (searchForm.pageNo - 1) * searchForm.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>

        <el-table-column
          prop="courseName"
          label="课程名"
          width="200"
        ></el-table-column>

        <el-table-column
          prop="cno"
          label="课程号"
          width="200"
        ></el-table-column>

        <el-table-column
          prop="credit"
          label="学分"
          width="180"
        ></el-table-column>

        <el-table-column label="课程状态" width="180">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 1">已分配</el-tag>
            <el-tag v-else type="danger">未分配</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          prop="totalNumber"
          label="课程容量"
          width="180"
        ></el-table-column>

        <el-table-column label="操作" width="240" class="floatRight">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="success"
              icon="el-icon-check"
              round
              @click="distributeCourse(scope.row.cno, teacherId, scope.row.totalNumber)"
              v-if="scope.row.status === 0"
            >分配
            </el-button>
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-close"
              round
              @click="withdrawCourse(scope.row.cno, teacherId)"
              v-else
            >取消
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
  import adminManage from "@/api/adminManage";

  export default {
    name: "AllCourse",
    data() {
      return {
        searchForm: {
          pageNo: 1,
          pageSize: 5,
          cno: "",
          courseName: "",
          teacherId: ''
        },
        courseList: [],
        cnoList: [],
        total: 0,
        currentPage: 1,
      }
    },
    computed: {
      teacherId() {
        return this.$route.query.teacherId;
      }
    },
    methods: {
      // 获取课程列表
      getCourseListDistributeTeacher() {

        console.log(this.searchForm);

        adminManage.getCourseListDistributeTeacher(this.searchForm).then(
          response => {
            this.courseList = response.data.courseList;
            this.total = response.data.total;
            this.cnoList = response.data.cnoList;

            // 要把 courseList 数组里面的每一个对象，加上一个属性 status，代表是否分配这个课程
            this.courseList = this.courseList.map((course) => {
              // 判断是否这门课被选择
              let isSelected = this.cnoList.some((cno) => {
                return cno === course.cno;
              });

              if (!course.hasOwnProperty('status')) {
                // 没有 status 属性，就添加一个 status 属性，默认值为 0
                this.$set(course, 'status', 0);
              }
              if (isSelected === true) course['status'] = 1;
              else course['status'] = 0;

              return course;
            });
          },
          error => {
            this.$message.error("获取课程失败");
          }
        )
      },
      // 分页数据
      handleSizeChange(pageSize) {
        this.searchForm.pageSize = pageSize;
        this.getCourseListDistributeTeacher();
      },
      handleCurrentChange(pageNo) {
        this.searchForm.pageNo = pageNo;
        this.getCourseListDistributeTeacher();
      },
      // 分配课程
      distributeCourse(cno, teacherId, totalNumber) {
        let data = {cno, teacherId, totalNumber};
        adminManage.distributeCourse(data).then(
          response => {
            // 课程状态改为 1 ，代表已分配
            this.courseList = this.courseList.map((course) => {
              if (course.cno === cno) {
                course.status = 1;
              }
              return course;
            });

            this.$message.success(response.msg);
          },
          error => {
            this.$message.error("分配课程失败");
          }
        )
      },
      // 取消分配课程
      withdrawCourse(cno, teacherId) {
        let data = {cno, teacherId};
        adminManage.withdrawCourse(data).then(
          response => {
            // 课程状态改为 1 ，代表已分配
            this.courseList = this.courseList.map((course) => {
              if (course.cno === cno) {
                course.status = 0;
              }
              return course;
            });

            this.$message.success(response.msg);
          },
          error => {
            this.$message.error("取消课程失败");
          }
        )
      }
    },
    mounted() {
      // 给教师 id 赋值
      this.searchForm.teacherId = this.teacherId;
      // 组件挂载获取课程列表
      this.getCourseListDistributeTeacher();
    }

  }
</script>

<style scoped type="text/css">
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
