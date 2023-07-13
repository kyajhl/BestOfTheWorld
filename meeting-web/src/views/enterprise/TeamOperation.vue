<template>
  <div>
    <div v-if="$route.name !== 'team_information'">
      <!-- 表单搜索 -->
      <el-card id="search">
        <el-row>
          <el-col :span="20">

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
                              项目号
                            </template>
                            {{ team.projectId }}
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
            prop="projectId"
            label="项目号"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model.number="teamForm.projectId"
              autocomplete="off"
              :disabled="teamForm.id === '777'"
            ></el-input>
          </el-form-item>

          <el-form-item
            prop="teamName"
            label="团队名"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model="teamForm.teamName"
              autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="已有学生"
            :label-width="formLabelWidth"
            v-if="teamForm.id === '777'"
          >
            <el-select
              v-model="teamForm.selectedStudentList"
              multiple
              placeholder="请选择"
            >
              <el-option
                v-for="item in teamForm.selectedStudentOnlyList"
                :key="item.value"
                :label="item.label"
                :value="item.value + '-' + item.position"
              >
                <span style="float: left; margin-right: 10px">{{ item.label }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
                <span
                  style="color: #8492a6; font-size: 13px; margin-right: 10px">{{ item.position }}</span>
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item
            v-if="teamForm.id === '777'"
          >
            <el-divider></el-divider>
          </el-form-item>

          <el-form-item
            label="添加待选学生"
            :label-width="formLabelWidth"
            v-if="teamForm.id === '777'"
          >
            <el-input
              v-model="mobilephone"
              autocomplete="off"
              placeholder="请输入学生手机号"
              style="width: 200px;margin-right: 20px"
            ></el-input>

            <el-select
              v-model="position"
              placeholder="请选择"
              style="margin-right: 20px"
            >
              <el-option
                v-for="item in positionList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>

            <el-button
              type="primary"
              @click="addStudent2Form"
            >
              添 加
            </el-button>

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
  import enterpriseManage from "@/api/enterpriseManage";

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
          teamId: '',
          projectId: '',
          teamName: '',
          volume: '',
          selectedStudentList: [
            // "111-项目经理",
            // "222-技术经理"
          ],

          selectedStudentOnlyList: [
            // {
            //   value: '111',
            //   label: '陈少荣',
            //   position: '项目经理'
            // },
            // {
            //   value: '222',
            //   label: '周杰伦',
            //   position: '技术经理'
            // },
            // {
            //   value: '333',
            //   label: '温荣森',
            //   position: '产品经理'
            // },
          ]
        },
        mobilephone: '',
        position: '',
        positionList: [
          {
            value: '项目经理',
            label: '项目经理'
          }, {
            value: '技术经理',
            label: '技术经理'
          }, {
            value: '产品经理',
            label: '产品经理'
          }, {
            value: '测试人员',
            label: '测试人员'
          }, {
            value: '开发人员',
            label: '开发人员'
          }
        ],
        rules: {
          projectId: [{required: true, message: '请输入项目号', trigger: 'blur'}],
          teamName: [{required: true, message: '请输入团队名', trigger: 'blur'}],
        },
        teamList: [],
        total: 0,
        currentPage: 1,
      }
    },
    watch: {
      'teamForm.selectedStudentList': {
        handler(value) {
          console.log(value)
        },
        deep: true,
        immediate: true
      },
    },
    methods: {
      // 添加学生到团队
      addStudent2Form() {
        // 先判断学生人数是否超过了项目容量
        if (this.teamForm.selectedStudentList.length === this.teamForm.volume) {
          this.$message.error("人数超过上限");
          // 清空表单
          this.mobilephone = '';
          this.position = '';
          return;
        }
        // 先判断手机号是否重复添加
        let isHasMobilePhone = this.teamForm.selectedStudentOnlyList.some(student => {
          return student.value === this.mobilephone;
        });
        if (isHasMobilePhone) {
          this.$message.error("手机号重复添加，请重新添加");
          // 清空表单
          this.mobilephone = '';
          this.position = '';
          return;
        }
        // 封装对象
        // label 为对应手机号的学生姓名
        // 先调用接口获取相应手机号的姓名
        enterpriseManage.getStudentName(this.mobilephone).then(
          response => {
            this.label = response.data.studentName;
            let studentInformation = {value: this.mobilephone, label: this.label, position: this.position};
            this.teamForm.selectedStudentOnlyList.push(studentInformation);
            let studentInformationStr = this.mobilephone + '-' + this.position;
            this.teamForm.selectedStudentList.push(studentInformationStr);
            // 清空表单
            this.mobilephone = '';
            this.position = '';
          },
          error => {
            this.$message.error("获取对应手机号的学生姓名失败");
          }
        );
      },
      // 获取团队列表
      getTeamList() {
        enterpriseManage.getTeamList(this.searchForm).then(
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
          this.teamForm.id = '777';
          // 清除 teamForm 里面的信息
          this.teamForm.selectedStudentOnlyList = [];
          this.teamForm.selectedStudentList = [];
          // 根据 teamId 查询团队信息
          enterpriseManage.getTeam(teamId).then(
            response => {
              this.$message.success(response.msg);
              // 这个 response.data 里面是否有 teamId ??? (没有)
              this.teamForm.volume = response.data.volume;
              this.teamForm.teamName = response.data.teamName;
              this.teamForm.projectId = response.data.projectId;
              this.teamForm.teamId = teamId;
              // selectedStudentList 和 selectedStudentOnlyList赋值，作为数据回显
              let selectStudentList = response.data.selectedStudentList;
              selectStudentList.map(selectedStudent => {
                // 先根据手机号查询学生姓名
                enterpriseManage.getStudentName(selectedStudent.mobilephone).then(
                  response => {
                    let studentName = response.data.studentName;
                    let studentInformationOnly = {
                      value: selectedStudent.mobilephone,
                      label: studentName,
                      position: selectedStudent.position
                    };
                    let studentInformation =
                      selectedStudent.mobilephone + '-' + selectedStudent.position;
                    this.teamForm.selectedStudentOnlyList.push(studentInformationOnly);
                    this.teamForm.selectedStudentList.push(studentInformation);
                  },
                  error => {
                    this.$message.error("获取学生姓名失败")
                  }
                );
              });
            },
            error => {
              this.$message.error("获取团队信息失败");
            }
          )
        }
        this.dialogFormVisible = true;
      },
      // 保存团队
      saveTeam() {
        this.$refs.teamFormRef.validate(
          (valid) => {
            if (valid) {
              enterpriseManage.saveTeam(this.teamForm).then(
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
          enterpriseManage.deleteTeam(team.teamId).then(
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
        // 删除 添加学生表单 的相关信息
        this.mobilephone = '';
        this.position = '';
      },
      // 分页数据
      handleSizeChange(pageSize) {
        this.searchForm.pageSize = pageSize;
        this.getTeamList();
      },
      handleCurrentChange(pageNo) {
        this.searchForm.pageNo = pageNo;
        this.getTeamList();
      }
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
