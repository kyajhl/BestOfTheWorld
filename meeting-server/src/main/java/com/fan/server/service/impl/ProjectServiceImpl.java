package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.server.common.Result;
import com.fan.server.pojo.Project;
import com.fan.server.mapper.ProjectMapper;
import com.fan.server.pojo.Student;
import com.fan.server.pojo.User;
import com.fan.server.service.IProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    @Override
    public void addProject(Project project) throws Exception {
        // 判断 student 表是否存在 mobilePhone
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getProjectId, project.getProjectId());
        Project project1 = this.getOne(wrapper);
        if (!Objects.isNull(project1)) {
            // 非空
            throw new Exception("项目号已存在");
        }
        // 空直接添加
        this.save(project);
    }

    @Override
    public void deleteProject(Integer projectId) throws Exception {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getProjectId, projectId);
        Project project1 = this.getOne(wrapper);
        if(Objects.isNull(project1)) {
            //未找到项目
            throw  new Exception(("项目号不存在"));
        }
        this.remove(wrapper);
    }

    @Override
    public void updateProject(Project project) throws Exception {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getProjectId, project.getProjectId());
        Project project1 = this.getOne(wrapper);
        if(Objects.isNull(project1)) {
            //未找到项目
            throw  new Exception(("项目号不存在"));
        }
        LocalDate beginDate = project.getBeginDate();
        LocalDate endDate = project.getEndDate();
        beginDate = beginDate.plusDays(1);
        endDate = endDate.plusDays(1);
        project.setBeginDate(beginDate);
        project.setEndDate((endDate));
        this.update(project, wrapper);
    }

    @Override
    public Map<String, Object> getProjectList(Long pageNo, Long pageSize, Integer projectId, String projectName){
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(projectId != null, Project::getProjectId, projectId);
        wrapper.like(Project::getProjectName, projectName);
        wrapper.orderByAsc(Project::getProjectId);

        IPage<Project> page = new Page<>(pageNo, pageSize);
        this.page(page, wrapper);

        //封装map
        HashMap<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
//        List<Project> projectList = page.getRecords();
//        projectList.forEach(project -> {
//            project.getBeginDate() = project.getBeginDate().toString();
//        });
        data.put("projectList", page.getRecords());

        return data;
    }

    @Override
    public Project getProjectById(Integer projectId) throws Exception{
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getProjectId, projectId);
        Project project = this.getOne(wrapper);
        if(Objects.isNull(project))
            throw new Exception("未找到项目");
        return project;
    }

}
