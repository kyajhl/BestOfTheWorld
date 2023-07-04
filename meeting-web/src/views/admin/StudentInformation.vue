<template>
  <div>
    <!-- 表单搜索 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchForm.studentId"
            placeholder="学号"
            @keyup.enter.native="getStudentList"
          ></el-input>
          <el-input
            v-model="searchForm.studentName"
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
        <el-col :span="4">
          <el-button
            type="primary"
            circle
            icon="el-icon-plus"
            class="floatRight"
            @click="openEditUI(null)"
          >
          </el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 数据展示 -->
    <el-card>
      <el-table :data="studentList" stripe style="width: 100%">

        <el-table-column label="#" width="180">
          <template slot-scope="scope">
            {{
            (searchForm.pageNo - 1) * searchForm.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>

        <el-table-column
          prop="studentId"
          label="学号"
          width="200"
        ></el-table-column>

        <el-table-column
          prop="name"
          label="学生名"
          width="200"
        ></el-table-column>

        <el-table-column
          prop="major"
          label="专业"
          width="200"
        ></el-table-column>

        <el-table-column
          prop="majorClass"
          label="专业班级"
          width="200"
        ></el-table-column>

        <el-table-column label="操作" width="200" class="floatRight">
          <template slot-scope="scope">
            <el-button
              @click="openEditUI(scope.row.studentId)"
              size="mini"
              type="primary"
              icon="el-icon-edit"
              circle
            ></el-button>
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-delete"
              circle
              @click="deleteStudent(scope.row)"
            ></el-button>
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

    <!-- 新增用户对话框 -->
    <el-dialog
      @close="clearForm"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="studentForm" :rules="rules" ref="studentFormRef">

        <el-form-item
          prop="studentId"
          label="学号"
          :label-width="formLabelWidth"
        >
          <el-input v-model="studentForm.studentId" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
          prop="name"
          label="学生名"
          :label-width="formLabelWidth"
        >
          <el-input v-model="studentForm.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
          prop="major"
          label="专业"
          :label-width="formLabelWidth"
        >
          <el-input v-model="studentForm.major" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
          prop="majorClass"
          label="专业班级"
          :label-width="formLabelWidth"
        >
          <el-input v-model="studentForm.majorClass" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveStudent">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import adminManage from "@/api/adminManage";

  export default {
    name: "StudentInformation",
    data() {
      return {
        formLabelWidth: "130px",
        studentForm: {
          studentId: '',
          name: '',
          major: '',
          majorClass: ''
        },
        rules: {
          studentId: [
            {required: true, message: '请输入学号', trigger: 'blur'},
          ],
          name: [
            {required: true, message: '请输入学生名', trigger: 'blur'},
          ],
          major: [
            {required: true, message: '请输入专业名', trigger: 'blur'},
          ],
          majorClass: [
            {required: true, message: '请输入专业班级', trigger: 'blur'},
          ],
        },
        dialogFormVisible: false,
        title: "",
        total: 0,
        searchForm: {
          pageNo: 1,
          pageSize: 5,
          studentName: '',
          studentId: ''
        },
        studentList: [],
        currentPage: 1,
      };
    },
    methods: {
      // 获取学生列表
      getStudentList() {
        adminManage.getStudentList(this.searchForm).then(
          response => {
            this.studentList = response.data.studentList;
            this.total = response.data.total;
          },
          error => {
            this.$message.error("获取学生列表失败");
          }
        )
      },

      // 编辑学生信息
      openEditUI(studentId) {
        if (studentId === null) {
          this.title = '添加学生';
          this.studentForm = {};
        } else {
          this.title = '修改学生';
          // 根据 studentId 查询学生信息
          adminManage.getStudent(studentId).then(
            response => {
              this.studentForm = response.data;
            },
            error => {
              this.$message.error("获取学生信息失败");
            }
          )
        }
        this.dialogFormVisible = true;
      },

      // 删除学生
      deleteStudent(student) {
        this.$confirm(`确认删除 ${student.name} ?`, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          adminManage.deleteStudent(student).then(
            response => {
              this.$message.success(response.msg);
              this.getStudentList();
            },
            error => {
              this.$message.error("删除学生失败");
            }
          )
        })
      },

      // 保存学生信息
      saveStudent() {
        this.$refs.studentFormRef.validate((valid) => {
          if (valid) {
            adminManage.saveStudent(this.studentForm).then(
              response => {
                this.$message.success(response.msg);
                this.dialogFormVisible = false;
                this.getStudentList();
              },
              error => {
                this.$message.error("操作失败!");
              }
            )
          } else {
            return false;
          }
        });
      },

      // 清除表单的信息
      clearForm() {
        // 清除表单验证提示信息
        this.$refs.studentFormRef.clearValidate();
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
      // 组件挂载获取学生列表
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
