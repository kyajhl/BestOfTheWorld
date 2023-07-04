<template>
  <div>
    <!-- 表单搜索 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">

          <el-input
            v-model.lazy="searchForm.courseName"
            placeholder="课程名"
            @keyup.enter.native="getTeachingCourseList"
          ></el-input>

          <el-input
            v-model.lazy="searchForm.cno"
            placeholder="课程号"
            @keyup.enter.native="getTeachingCourseList"
          ></el-input>

          <el-button
            type="primary"
            round
            icon="el-icon-search"
            @click="getTeachingCourseList"
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
      <el-table :data="courseList" stripe style="width: 100%">

        <el-table-column label="#" width="150">
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
          prop="selectedNumber"
          label="已选人数"
          width="210"
        ></el-table-column>

        <el-table-column
          prop="totalNumber"
          label="课程容量"
          width="210"
        ></el-table-column>


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
  import teacherManage from "@/api/teacherManage";

  export default {
    name: "TeachingCourse",
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
        total: 0,
        currentPage: 1,
      }
    },
    computed: {
      ...mapGetters(["name"]),

    },
    methods: {
      // 获取教师教授课程的列表
      getTeachingCourseList() {
        teacherManage.getTeachingCourseList(this.searchForm).then(
          response => {
            this.courseList = response.data.teachingCourseList;
            this.total = response.data.total;
          },
          error => {
            this.$message.error("获取教授课程失败");
          }
        )
      },

      // 分页数据
      handleSizeChange(pageSize) {
        this.searchForm.pageSize = pageSize;
        this.getTeachingCourseList();
      },
      handleCurrentChange(pageNo) {
        this.searchForm.pageNo = pageNo;
        this.getTeachingCourseList();
      }

    },
    mounted() {
      // 给教师 id 赋值
      this.searchForm.teacherId = this.name;
      this.getTeachingCourseList();
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
