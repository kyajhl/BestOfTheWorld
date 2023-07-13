<template>
  <div>
    <div v-if="$route.name !== 'project_information'">
      <!-- 表单搜索 -->
      <el-card id="search">
        <el-row>
          <el-col :span="20">

            <el-input
              v-model.lazy="searchForm.projectId"
              placeholder="项目号"
              @keyup.enter.native="getProjectList"
            ></el-input>

            <el-input
              v-model.lazy="searchForm.projectName"
              placeholder="项目名"
              @keyup.enter.native="getProjectList"
            ></el-input>

            <el-button
              type="primary"
              round
              icon="el-icon-search"
              @click="getProjectList"
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
                <span>项目列表</span>
              </div>
              <div v-for="project in projectList" :key="project.projectId" class="text">
                <el-row :gutter="20">
                  <el-col :span="20">
                    <router-link
                      :to="`/enterprise/project_operation/project_information?projectId=${project.projectId}`"
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
                              项目号
                            </template>
                            {{ project.projectId }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              项目名
                            </template>
                            {{ project.projectName }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              开始时间
                            </template>
                            {{ project.beginDate }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              结束时间
                            </template>
                            {{ project.endDate }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              人数容量
                            </template>
                            {{ project.volume }}
                          </el-descriptions-item>

                        </el-descriptions>
                      </div>
                    </router-link>
                  </el-col>
                  <el-col
                    :span="4"
                    class="operation"
                  >
                    <el-descriptions
                      :column="2"
                      size="medium"
                      border
                    >
                      <el-descriptions-item>
                        <template slot="label">
                          操作
                        </template>
                        <el-button
                          @click="openEditUI(project.projectId)"
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
                          @click="deleteProject(project)"
                        ></el-button>
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
      >
      </el-pagination>

      <!-- 新增用户对话框 -->
      <el-dialog
        @close="clearForm"
        :title="title"
        :visible.sync="dialogFormVisible"
      >
        <el-form :model="projectForm" :rules="rules" ref="projectFormRef">

          <el-form-item
            prop="projectId"
            label="项目号"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model.number="projectForm.projectId"
              autocomplete="off"
              placeholder="请填数字"
              :disabled="this.title === '修改项目'"
            ></el-input>
          </el-form-item>

          <el-form-item
            prop="projectName"
            label="项目名"
            :label-width="formLabelWidth"
          >
            <el-input v-model="projectForm.projectName" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item
            label="开始日期"
            prop="beginDate"
            :label-width="formLabelWidth"
          >
            <el-col :span="10">
              <el-date-picker type="date" placeholder="选择开始日期" v-model="projectForm.beginDate"
                              style="width: 100%;"></el-date-picker>
            </el-col>
          </el-form-item>

          <el-form-item
            prop="endDate"
            label="结束日期"
            :label-width="formLabelWidth"
          >
            <el-col :span="10">
              <el-date-picker type="date" placeholder="选择结束日期" v-model="projectForm.endDate"
                              style="width: 100%;"></el-date-picker>
            </el-col>
          </el-form-item>

          <el-form-item
            prop="content"
            label="项目简介"
            :label-width="formLabelWidth"
          >
            <el-input
              type="textarea"
              :rows="5"
              placeholder="请输入项目简介"
              v-model="projectForm.content">
            </el-input>
          </el-form-item>

          <el-form-item
            prop="volume"
            label="人数容量"
            :label-width="formLabelWidth"
          >
            <el-input v-model.number="projectForm.volume" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item
            prop="acceptanceCriteria"
            label="验收标准"
            :label-width="formLabelWidth"
          >
            <el-input
              type="textarea"
              :rows="5"
              placeholder="请输入验收标准"
              v-model="projectForm.acceptanceCriteria">
            </el-input>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveProject">确 定</el-button>
        </div>
      </el-dialog>

    </div>
    <!--  显示三级路由  -->
    <router-view></router-view>
  </div>
</template>

<script>
  import enterpriseManage from "@/api/enterpriseManage";

  export default {
    name: "ProjectOperation",
    data() {
      return {
        formLabelWidth: "130px",
        dialogFormVisible: false,
        title: '',
        searchForm: {
          pageNo: 1,
          pageSize: 5,
          projectId: '',
          projectName: ''
        },
        projectForm: {
          projectId: '',
          projectName: '',
          beginDate: '',
          endDate: '',
          content: '',
          volume: '',
          acceptanceCriteria: ''
        },
        rules: {
          projectId: [
            {required: true, message: '请输入项目号', trigger: 'blur'},
          ],
          projectName: [
            {required: true, message: '请输入项目名', trigger: 'blur'},
          ],
          beginDate: [
            {required: true, message: '请输入开始日期', trigger: 'blur'},
          ],
          endDate: [
            {required: true, message: '请输入结束日期', trigger: 'blur'},
          ],
          content: [
            {required: true, message: '请输入项目简介', trigger: 'blur'},
          ],
          volume: [
            {required: true, message: '请输入人数容量', trigger: 'blur'},
          ],
          acceptanceCriteria: [
            {required: true, message: '请输入验收标准', trigger: 'blur'},
          ],
        },
        projectList: [],
        total: 0,
        currentPage: 1,
      }
    },
    methods: {
      // 获取项目列表
      getProjectList() {
        enterpriseManage.getProjectList(this.searchForm).then(
          response => {
            this.projectList = response.data.projectList;
            this.total = response.data.total;
            this.projectList = this.projectList.map(project => {
              project.beginDate = project.beginDate.join('-');
              project.endDate = project.endDate.join('-');
              return project;
            });
          },
          error => {
            this.$message.error("获取项目列表失败");
          }
        )
      },
      // 编辑表单
      openEditUI(projectId) {
        if (projectId === null) {
          this.title = '添加项目';
          this.projectForm = {id: '666'};
        } else {
          this.title = '修改项目';
          // 根据 projectId 查询项目信息
          enterpriseManage.getProject(projectId).then(
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
              this.$message.error("获取项目信息失败");
            }
          )
        }
        this.dialogFormVisible = true;
      },
      // 保存项目
      saveProject() {
        this.$refs.projectFormRef.validate(
          (valid) => {
            if (valid) {
              enterpriseManage.saveProject(this.projectForm).then(
                response => {
                  this.$message.success(response.msg);
                  this.dialogFormVisible = false;
                  this.getProjectList();
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
      // 删除项目
      deleteProject(project) {
        this.$confirm(`确认删除 ${project.projectName} ?`, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          enterpriseManage.deleteProject(project.projectId).then(
            response => {
              this.$message.success(response.msg);
              this.getProjectList();
            },
            error => {
              this.$message.error("删除项目失败");
            }
          )
        })
      },
      // 清除表单的信息
      clearForm() {
        // 清除表单验证提示信息
        this.$refs.projectFormRef.clearValidate();
      },
      // 分页数据
      handleSizeChange(pageSize) {
        this.searchForm.pageSize = pageSize;
        this.getProjectList();
      },
      handleCurrentChange(pageNo) {
        this.searchForm.pageNo = pageNo;
        this.getProjectList();
      },
      // 把日期字符串转换为日期
      getDateBySplit(currentTime) {
        let dateArray = currentTime.split('-');
        let year = dateArray[0];
        let month = dateArray[1] - 1;
        let day = dateArray[2];
        return new Date(year, month, day);
      }
    },
    mounted() {
      this.getProjectList();
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
