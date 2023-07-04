<template>
  <div>
    <!-- 表单搜索 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">

          <el-input
            v-model.lazy="searchForm.courseName"
            placeholder="课程名"
            @keyup.enter.native="getCourseList"
          ></el-input>

          <el-input
            v-model.lazy="searchForm.cno"
            placeholder="课程号"
            @keyup.enter.native="getCourseList"
          ></el-input>

          <el-button
            type="primary"
            round
            icon="el-icon-search"
            @click="getCourseList"
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
          width="150"
        ></el-table-column>

        <el-table-column
          prop="cno"
          label="课程号"
          width="150"
        ></el-table-column>

        <el-table-column
          prop="name"
          label="授课教师"
          width="150"
        ></el-table-column>

        <el-table-column
          prop="credit"
          label="学分"
          width="150"
        ></el-table-column>

        <el-table-column
          prop="totalNumber"
          label="课程容量"
          width="150"
        ></el-table-column>

        <el-table-column
          prop="optionalNumber"
          label="剩余容量"
          width="150"
        ></el-table-column>

        <el-table-column label="课程状态">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 1">已选择</el-tag>
            <el-tag v-else type="danger">未选择</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="130" class="floatRight">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="success"
              icon="el-icon-check"
              round
              @click="selectedCourse(scope.row.cno, name, scope.row.teacherId, scope.row.optionalNumber)"
              v-if="scope.row.status === 0"
            >选择
            </el-button>
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-close"
              round
              @click="withdrawCourse(scope.row.cno, name, scope.row.teacherId)"
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
  import studentManage from '../../api/studentManage'
  import {mapGetters} from 'vuex'

  export default {
    name: "CourseSelectionSystem",
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
        cnoList: [],
        teacherIdList: [],
        total: 0,
        currentPage: 1,
      }
    },
    computed: {
      ...mapGetters(["name"]),

    },
    methods: {
      // 获取课程列表
      getCourseList() {
        studentManage.getAllCourseList(this.searchForm).then(
          response => {
            this.courseList = response.data.courseList;
            this.cnoList = response.data.cnoList;
            this.total = response.data.total;
            this.teacherIdList = response.data.teacherIdList;

            console.log(this.courseList);

            // 要把 courseList 数组里面的每一个对象，加上一个属性 status，代表是否选择这个课程
            this.courseList = this.courseList.map((course) => {
              // 判断是否这门课被选择
              let isSelected = false;
              for (let i = 0; i < this.cnoList.length; i++) {
                if (course.cno === this.cnoList[i] && course.teacherId === this.teacherIdList[i]) {
                  isSelected = true;
                  break;
                }
              }

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
            this.$message.error("获取课程失败")
          }
        )
      },
      // 改变分页的参数，不需要加实参，Element-UI 自动提供，但是要加形参
      handleSizeChange(pageSize) {
        this.searchForm.pageSize = pageSize;
        this.getCourseList();
      },
      handleCurrentChange(pageNo) {
        this.searchForm.pageNo = pageNo;
        this.getCourseList();
      },
      // 选择课程，通过传递课程号，增加一条选课记录
      selectedCourse(cno, name, teacherId, optionalNumber) {
        // 判断课程的剩余数量是否 > 0，如果 <= 0，则不能再选择此课程
        if (optionalNumber <= 0) this.$message.error("此课程被抢完了，练下手速吧(●ˇ∀ˇ●)");
        else {
          // 封装对象
          let data = {cno, studentId: name, teacherId};
          studentManage.addSelectedCourse(data).then(
            response => {
              // 选择课程成功
              if (response.data === true) {
                this.$message.success(response.msg);
                // 把该课程的 status 变为 1
                this.courseList = this.courseList.map((course) => {
                  if (course.cno === cno && course.teacherId === teacherId) {
                    course.status = 1;
                    course.optionalNumber = course.optionalNumber - 1;
                  }
                  return course;
                });
              }
              // 选择课程失败(已选)
              else this.$message.error(response.msg);
            },
            error => {
            }
          )
        }
      },

      // 取消选择课程
      withdrawCourse(cno, name, teacherId) {
        // 封装对象
        let data = {cno, studentId: name, teacherId};
        studentManage.deleteSelectedCourse(data).then(
          response => {
            // 把该课程的 status 变为 0
            this.courseList = this.courseList.map((course) => {
              if (course.cno === cno && course.teacherId === teacherId) {
                course.status = 0;
                course.optionalNumber = course.optionalNumber + 1;
              }
              return course;
            });
            // 这里直接获取课程列表
            // this.getCourseList();
            // 取消课程成功
            this.$message.success(response.msg);
          },
          error => {
            this.$message.error("取消课程失败")
          }
        )
      }

    },
    mounted() {
      // 给学生 id 赋值
      this.searchForm.studentId = this.name;
      // 每次组件挂载，就调用一次获取课程列表函数
      this.getCourseList();
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
