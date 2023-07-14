<template>
  <div>
    <div v-if="$route.name !== 'student_team_information'">
      <!-- 表单搜索 -->
      <el-card id="search">
        <el-row>
          <el-col :span="16">

            <el-input
              v-model.lazy="searchForm.teamName"
              placeholder="团队名"
              @keyup.enter.native="getTeamList"
            ></el-input>

            <el-button
              type="primary"
              round
              icon="el-icon-search"
              @click="getTeamList"
            >查询
            </el-button>

          </el-col>
          <el-col :span="8">
            <el-button
              type="primary"
              class="floatRight"
              round
              style="margin-right: 10px"
              @click="openDialog"
            >
              发布个人日志
            </el-button>
          </el-col>
        </el-row>
      </el-card>

      <!-- 数据展示 -->
      <div>
        <el-row :gutter="20">

          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>团队列表</span>
              </div>
              <div v-for="team in teamList" :key="team.teamId" class="text">
                <el-row :gutter="20">
                  <el-col :span="24">
                    <router-link
                      :to="`/student/student_operation/student_team_information?teamId=${team.teamId}`"
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
                              项目名
                            </template>
                            {{ team.projectName }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              团队名
                            </template>
                            {{ team.teamName }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              团队人数
                            </template>
                            {{ team.teamNumber }}
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
      >
      </el-pagination>

      <!-- 日志和实训总结表单 -->
      <el-dialog
        @close="clearForm"
        :title="title"
        :visible.sync="dialogFormVisible"
      >

        <el-form
          :model="logsForm"
          :rules="rules"
          ref="logsFormRef"
        >
          <el-form-item
            prop="logsContent"
            label="日志"
            :label-width="formLabelWidth"
          >
            <el-input
              type="textarea"
              :rows="5"
              placeholder="请输入日志"
              v-model="logsForm.logsContent">
            </el-input>
          </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveInformation">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <!--  显示三级路由  -->
    <router-view></router-view>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";
  import studentManage from "@/api/studentManage";

  export default {
    name: "StudentOperation",
    data() {
      return {
        formLabelWidth: '100px',
        dialogFormVisible: false,
        searchForm: {
          pageNo: 1,
          pageSize: 5,
          teamName: '',
          mobilephone: ''
        },
        teamList: [],
        total: 0,
        currentPage: 1,
        logsForm: {
          logsContent: '',
          mobilephone: ''
        },
        summaryForm: {
          content: '',
          studentId: ''
        },
        title: '',
        rules: {
          logsContent: [
            {required: true, message: '请填写日志', trigger: 'blur'}
          ]
        }
      }
    },
    computed: {
      ...mapGetters(["mobilephone", "studentId"])
    },
    methods: {
      // 打开表单
      openDialog() {
        // 日志
        this.title = '发布日志';
        this.dialogFormVisible = true;
        this.logsForm = {
          mobilephone: this.mobilephone
        };
      },
      // 保存信息
      saveInformation() {
        this.$refs['logsFormRef'].validate((valid) => {
          if (valid) {
            // 保存日志
            studentManage.addPersonLogs(this.logsForm).then(
              response => {
                this.$message.success(response.msg);
              },
              error => {
                this.$message.error("添加日志失败");
              }
            );
            this.dialogFormVisible = false;
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      // 获取团队列表
      getTeamList() {
        studentManage.getTeamList(this.searchForm).then(
          response => {
            this.teamList = response.data.teamList;
            this.total = response.data.total;
            console.log(this.teamList);
          },
          error => {
            this.$message.error("获取已选团队失败");
          }
        )
      },
      // 清除表单的信息
      clearForm() {
        // 清除表单验证提示信息
        this.$refs.logsFormRef.clearValidate();
      },
      // 改变分页的参数，不需要加实参，Element-UI 自动提供，但是要加形参
      handleSizeChange(pageSize) {
        this.searchForm.pageSize = pageSize;
        this.getTeamList();
      },
      handleCurrentChange(pageNo) {
        this.searchForm.pageNo = pageNo;
        this.getTeamList();
      },
    },
    mounted() {
      // 赋值
      this.searchForm.mobilephone = this.mobilephone;
      this.logsForm.mobilephone = this.mobilephone;
      this.summaryForm.studentId = this.studentId;
      this.getTeamList();
    }
  }
</script>

<style scoped>
  .el-dialog .el-input {
    width: 85%;
  }

  .floatRight {
    float: right;
  }

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
