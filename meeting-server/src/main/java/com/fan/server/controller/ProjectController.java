package com.fan.server.controller;


import com.fan.server.common.Result;
import com.fan.server.pojo.Project;
import com.fan.server.pojo.User;
import com.fan.server.service.IProjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @PostMapping("/addProject")
    @ApiOperation("创建项目接口")
    public Result<?> addProject(@RequestBody Project project) {
        try {
            projectService.addProject(project);
            return Result.success("创建项目成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }

}
