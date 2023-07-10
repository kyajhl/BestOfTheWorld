package com.fan.server.service;

import com.fan.server.pojo.Project;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.parameters.P;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
public interface IProjectService extends IService<Project> {

    void addProject(Project project) throws Exception;

    void deleteProject(Integer projectId) throws Exception;

    void updateProject(Project project) throws Exception;

    Map<String, Object> getProjectList(Long pageNo, Long pageSize, Integer projectId, String projectName);

    Project getProjectById(Integer projectId) throws Exception;

}
