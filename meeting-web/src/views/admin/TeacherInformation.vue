<template>
  <div>
    <div v-if="$route.name !== 'teacher_course'">
      <!-- 表单搜索 -->
      <el-card id="search">
        <el-row>
          <el-col :span="20">

            <el-input
              v-model.lazy="searchForm.teacherName"
              placeholder="教师名"
              @keyup.enter.native="getTeacherList"
            ></el-input>

            <el-input
              v-model.lazy="searchForm.teacherId"
              placeholder="教师工号"
              @keyup.enter.native="getTeacherList"
            ></el-input>

            <el-button
              type="primary"
              round
              icon="el-icon-search"
              @click="getTeacherList"
            >查询
            </el-button>

          </el-col>
          <el-col :span="4">
          </el-col>
        </el-row>
      </el-card>

      <!-- 数据列表 -->
      <div>
        <el-row :gutter="20">

          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>教师列表</span>
              </div>
              <div v-for="teacher in teacherList" :key="teacher.teacherId" class="text">
                <router-link
                  :to="`/admin/teacher_information/teacher_course?teacherId=${teacher.teacherId}`"
                  class="item"
                >

                  <el-row :gutter="20">
                    <el-col :span="22">
                      <div class="grid-content">
                        <el-descriptions class="margin-top"
                                         :column="4"
                                         size="medium"
                                         border
                                         style="width: 90%"
                        >

                          <el-descriptions-item>
                            <template slot="label">
                              教师名
                            </template>
                            {{ teacher.name }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              教师工号
                            </template>
                            {{ teacher.teacherId }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              性别
                            </template>
                            {{ teacher.sex }}
                          </el-descriptions-item>

                          <el-descriptions-item>
                            <template slot="label">
                              电话
                            </template>
                            {{ teacher.telephone }}
                          </el-descriptions-item>

                        </el-descriptions>
                      </div>
                    </el-col>
                    <el-col :span="2">
                      <div class="grid-content">
                        <span class="el-icon-arrow-right rightFloat"></span>
                      </div>
                    </el-col>
                  </el-row>

                </router-link>
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

    </div>
    <!--  显示三级路由  -->
    <router-view></router-view>
  </div>
</template>

<script>
  import adminManage from "@/api/adminManage";

  export default {
    name: "TeacherInformation",
    data() {
      return {
        searchForm: {
          pageNo: 1,
          pageSize: 5,
          teacherId: '',
          teacherName: ''
        },
        teacherList: [],
        total: 0,
        currentPage: 1,
      }
    },
    methods: {
      // 获取教师信息列表
      getTeacherList() {
        adminManage.getTeacherList(this.searchForm).then(
          response => {
            this.teacherList = response.data.teacherList;
            this.total = response.data.total;
          },
          error => {
            this.$message.error("获取教师列表失败");
          }
        )
      },

      // 分页数据
      handleSizeChange(pageSize) {
        this.searchForm.pageSize = pageSize;
        this.getTeacherList();
      },
      handleCurrentChange(pageNo) {
        this.searchForm.pageNo = pageNo;
        this.getTeacherList();
      }
    },
    mounted() {
      // 组件挂载获取教师信息列表
      this.getTeacherList();
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
