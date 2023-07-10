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
              <el-date-picker type="date" placeholder="选择日期" v-model="projectForm.beginDate"
                              style="width: 100%;"></el-date-picker>
            </el-col>
          </el-form-item>

          <el-form-item
            prop="endDate"
            label="结束日期"
            :label-width="formLabelWidth"
          >
            <el-col :span="10">
              <el-date-picker type="date" placeholder="选择日期" v-model="projectForm.endDate"
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
              :rows="2"
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
              :rows="2"
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
  export default {
    name: "TeamOperation",
    data() {
      return {

      }
    }

  }
</script>

<style scoped>

</style>
