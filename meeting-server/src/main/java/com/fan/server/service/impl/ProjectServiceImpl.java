package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.util.Objects;

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

}
