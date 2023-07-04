<template>
  <div>
    <!-- 表单搜索 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">

          <el-input
            v-model.lazy="searchForm.studentName"
            placeholder="学生名"
            @keyup.enter.native="getStudentList"
          ></el-input>

          <el-input
            v-model.lazy="searchForm.studentId"
            placeholder="学号"
            @keyup.enter.native="getStudentList"
          ></el-input>

          <el-button
            type="primary"
            round
            icon="el-icon-search"
            @click="getStudentList"
          >查询
          </el-button
          >

        </el-col>
        <el-col :span="4">
        </el-col>
      </el-row>
    </el-card>

    <!-- 数据展示 -->
    <el-card>
      <el-table :data="studentList" stripe style="width: 100%">

        <el-table-column label="#" width="150">
          <template slot-scope="scope">
            {{
            (searchForm.pageNo - 1) * searchForm.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>

        <el-table-column
          prop="studentId"
          label="学号"
          width="180"
        ></el-table-column>

        <el-table-column
          prop="studentName"
          label="学生名"
          width="180"
        ></el-table-column>

        <el-table-column
          prop="major"
          label="专业"
          width="160"
        ></el-table-column>

        <el-table-column
          prop="majorClass"
          label="专业班级"
          width="180"
        ></el-table-column>

        <el-table-column
          prop="submitted"
          label="提交状态"
          width="180"
        >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.submitted === 1">已提交</el-tag>
            <el-tag v-else type="danger">未提交</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="130" class="floatRight">
          <template slot-scope="scope">
            <el-input
              v-model="scope.row.grade"
              placeholder="输入分数"
              size="small"
              @blur="submitGrade(scope.row.studentId, cno, scope.row.grade)"
            ></el-input>
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
  import teacherManage from "@/api/teacherManage";
  import studentManage from "@/api/studentManage";

  export default {
    name: "AllStudent",
    data() {
      return {
        searchForm: {
          pageNo: 1,
          pageSize: 5,
          studentId: "",
          studentName: "",
          teacherId: '',
          cno: ''
        },
        studentList: [],
        total: 0,
        currentPage: 1,
      }
    },
    computed: {
      teacherId() {
        return this.$route.query.teacherId;
      },
      cno() {
        return this.$route.query.cno;
      }
    },
    methods: {
      // 获取学生列表
      getStudentList() {
        teacherManage.getStudentList(this.searchForm).then(
          response => {
            this.total = response.data.total;
            this.studentList = response.data.studentList;
          },
          error => {
            this.$message.error("获取学生列表失败");
          }
        )
      },

      // 提交分数
      submitGrade(studentId, cno, grade) {
        if (grade !== null && grade !== '') {
          // 封装对象
          let data = {studentId, cno, grade};
          studentManage.modifyStudentGrade(data).then(
            response => {
              // 遍历学生列表解决数据回显问题
              this.studentList = this.studentList.map((student) => {
                if (student.studentId === studentId) {
                  student.submitted = 1;
                  student.grade = grade;
                }
                return student;
              });

              this.$message.success(response.msg);
            },
            error => {
              this.$message.error("修改学生成绩失败");
            }
          )
        }
        this.setTimeout(() => {
          this.getStudentList();
        }, 0.3)
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
      // 给教师 id 和 课程号 cno 赋值
      this.searchForm.teacherId = this.teacherId;
      this.searchForm.cno = this.cno;
      // 组件只要挂载，就获取学生列表
      this.getStudentList();
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
