<template>
  <div>
    <!-- 表单搜索 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">

          <el-input
            v-model.lazy="searchForm.courseName"
            placeholder="课程名"
            @keyup.enter.native="getSelectedCourseAndGradeList"
          ></el-input>

          <el-input
            v-model.lazy="searchForm.cno"
            placeholder="课程号"
            @keyup.enter.native="getSelectedCourseAndGradeList"
          ></el-input>

          <el-button
            type="primary"
            round
            icon="el-icon-search"
            @click="getSelectedCourseAndGradeList"
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
          width="180"
        ></el-table-column>

        <el-table-column
          prop="cno"
          label="课程号"
          width="180"
        ></el-table-column>

        <el-table-column
          prop="finalCredit"
          label="学分"
          width="180"
        ></el-table-column>

        <el-table-column
          prop="name"
          label="授课教师"
          width="180"
        ></el-table-column>

        <el-table-column
          prop="grade"
          label="课程成绩"
          width="180"
        ></el-table-column>

        <el-table-column
          prop="grade"
          label="成绩评价"
          width="180"
        >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.grade >= 90">优秀</el-tag>
            <el-tag v-else-if="scope.row.grade >= 80 && scope.row.grade < 90" type="success">良好</el-tag>
            <el-tag v-else-if="scope.row.grade >= 60 && scope.row.grade < 80" type="warning">及格</el-tag>
            <el-tag v-else type="danger">不及格</el-tag>
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
  import {mapGetters} from 'vuex'
  import studentManage from '@/api/studentManage'

  export default {
    name: "CourseGrade",
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
      // 获取课程成绩列表
      getSelectedCourseAndGradeList() {
        studentManage.getSelectedCourseAndGradeList(this.searchForm).then(
          response => {
            this.courseList = response.data.selectedCourseAndGradeList;
            this.total = response.data.total;

          },
          error => {
            this.$message.error("获取成绩失败");
          }
        )
      },

      // 分页展示
      handleSizeChange(pageSize) {
        this.searchForm.pageSize = pageSize;
        this.getSelectedCourseAndGradeList();
      },
      handleCurrentChange(pageNo) {
        this.searchForm.pageNo = pageNo;
        this.getSelectedCourseAndGradeList();
      }
    },
    mounted() {
      // 给学生 id 赋值
      this.searchForm.studentId = this.name;
      // 获取已选择的课程成绩列表
      this.getSelectedCourseAndGradeList();
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
