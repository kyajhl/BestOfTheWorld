package com.fan.server.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.fan.server.common.Result;
import com.fan.server.pojo.Project;
import com.fan.server.pojo.User;
import com.fan.server.service.IProjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/deleteProject")
    @ApiOperation("删除项目接口")
    public Result<?> deleteProject(@RequestBody Integer projectId) {
        try{
            projectService.deleteProject(projectId);
            return Result.success("删除项目成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail(202);
    }

    @PutMapping("/updateProject")
    @ApiOperation("更新项目接口")
    public Result<?> updateProject(@RequestBody Project project) {
        try{
            projectService.updateProject(project);
            return Result.success("更新项目成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail(202);
    }

}
