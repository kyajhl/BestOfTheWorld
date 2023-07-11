<template>
  <div>
    <div>
      <!-- 表单搜索 -->
      <el-card id="search">
        <el-row>
          <el-col :span="20">

            <el-input
              v-model.lazy="searchForm.teamName"
              placeholder="团队号"
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

      <!-- 数据列表 -->
      <div>
        <el-row :gutter="20">

          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>团队列表</span>
              </div>
              <div v-for="team in teamList" :key="team.teamId" class="text">
                <el-row :gutter="20">
                  <el-col :span="20">
                    <router-link
                      :to="`/enterprise/team_operation/team_information?teamId=${team.teamId}`"
                      class="item"
                    >
                      <div class="grid-content">
                        <el-descriptions class="margin-top"
                                         :column="4"
                                         size="medium"
                                         border
                                         style="width: 100%"
                        >
                          <el-descriptions-item>
                            <template slot="label">
                              团队号
                            </template>
                            {{ team.teamId }}
                          </el-descriptions-item>
                          <el-descriptions-item>
                            <template slot="label">
                              项目号
                            </template>
                            {{ team.projectId }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              团队名字
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
                  <el-col :span="4" class="operation">
                    <el-descriptions :column="2" size="medium" border>
                      <el-descriptions-item>
                        <template slot="label">
                          操作
                        </template>
                        <el-button @click="openEditUI(team.teamId)" size="mini" type="primary" icon="el-icon-edit"
                                   circle></el-button>
                        <el-button size="mini" type="danger" icon="el-icon-delete" circle
                                   @click="deleteTeam(team)"></el-button>
                      </el-descriptions-item>
                    </el-descriptions>
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

      <!-- 新增用户对话框 -->
      <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
        <el-form :model="teamForm" :rules="rules" ref="teamFormRef">

          <el-form-item
            prop="teamName"
            label="团队名字"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model="teamForm.teamName"
              autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            prop="projectId"
            label="项目号"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model.number="teamForm.projectId"
              autocomplete="off"
            ></el-input>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveTeam">确 定</el-button>
        </div>
      </el-dialog>

    </div>
    <!--  显示三级路由  -->
    <router-view></router-view>
  </div>
</template>

<script>
  import team from "@/api/team";

  export default {
    name: "TeamOperation",
    data() {
      return {
        formLabelWidth: "130px",
        dialogFormVisible: false,
        title: '',
        searchForm: {
          pageNo: 1,
          pageSize: 5,
          teamName: ''
        },
        teamForm: {
          projectId: '',
          teamName: '',
        },
        rules: {
          projectId: [{
            required: true,
            message: '请输入项目号',
            trigger: 'blur'
          },],
          teamName: [{
            required: true,
            message: '请输入团队名字',
            trigger: 'blur'
          },],
        },
        teamList: [],
        total: 0,
        currentPage: 1,
      }
    },
    methods: {
      // 获取团队列表出 没改
      getTeamList() {
        team.getTeamList(this.searchForm).then(
          response => {
            this.teamList = response.data.teamList;
            this.total = response.data.total;
          },
          error => {
            this.$message.error("获取团队列表失败");
          }
        )
      },
      // 编辑表单
      openEditUI(teamId) {
        if (teamId === null) {
          this.title = '添加团队';
          this.teamForm = {
            id: '666'
          };
        } else {
          this.title = '修改团队';
          // 根据 teamId 查询团队信息 没改
          team.getTeam(teamId).then(
            response => {
              this.$message.success(response.msg);
              this.projectForm = response.data;
              this.projectForm.id = '777';
              this.projectForm.beginDate = this.projectForm.beginDate.join('-');
              this.projectForm.beginDate = this.getDateBySplit(this.projectForm.beginDate);
              this.projectForm.endDate = this.projectForm.endDate.join('-');
              this.projectForm.endDate = this.getDateBySplit(this.projectForm.endDate);
            },
            error => {
              this.$message.error("获取团队信息失败");
            }
          )
        }
        this.dialogFormVisible = true;
      },
      // 保存项目
      saveTeam() {
        this.$refs.teamFormRef.validate(
          (valid) => {
            if (valid) {
              team.saveTeam(this.teamForm).then(
                response => {
                  this.$message.success(response.msg);
                  this.dialogFormVisible = false;
                  this.getTeamList();
                },
                error => {
                  this.$message.error("操作失败!");
                }
              )
            } else {
              return false;
            }
          }
        );
      },
      // 删除团队
      deleteTeam(team) {
        this.$confirm(`确认删除 ${team.teamName} ?`, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          team.deleteTeam(team.teamId).then(
            response => {
              this.$message.success(response.msg);
              this.getTeamList();
            },
            error => {
              this.$message.error("删除团队失败");
            }
          )
        })
      },
      // 清除表单的信息
      clearForm() {
        // 清除表单验证提示信息
        this.$refs.teamFormRef.clearValidate();
      },
      // 分页数据
      handleSizeChange(pageSize) {
        this.searchForm.pageSize = pageSize;
        this.getTeamList();
      },
      handleCurrentChange(pageNo) {
        this.searchForm.pageNo = pageNo;
        this.getTeamList();
      },
      // 把日期字符串转换为日期

    },
    mounted() {
      this.getTeamList();
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
