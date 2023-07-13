package com.fan.server.controller;


import com.fan.server.common.Result;
import com.fan.server.pojo.Project;
import com.fan.server.pojo.Team;
import com.fan.server.service.ITeamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private ITeamService teamService;

    @PostMapping("/addTeam")
    @ApiOperation("添加团队接口")
    public Result<?> addTeam(@RequestBody Team team) {
        try {
            teamService.addTeam(team.getTeamName(), team.getProjectId());
            return Result.success("添加团队成功");
        } catch (Exception e) {
            return Result.fail("团队不存在");
        }
    }

    @DeleteMapping("/deleteTeam")
    @ApiOperation("删除团队接口")
    public Result<?> delete(@RequestParam String teamId) {
        try {
            teamService.deleteTeam(teamId);
            return Result.success("删除团队成功");
        } catch (Exception e) {
            return Result.fail("团队不存在");
        }
    }

    @GetMapping("/getTeamList")
    @ApiOperation("获取所有团队接口")
    public Result<Map<String, Object>> getTeamList(
            @RequestParam("pageNo") Long pageNo,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam(required = false, value = "teamName") String teamName
    ) {
        try {
            Map<String, Object> data = teamService.getTeamList(pageNo, pageSize, teamName);
            return Result.success(data, "获取团队列表成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }

    @PostMapping("/updateTeam")
    @ApiOperation("更新团队信息接口")
    public Result<?> updateProject(@RequestBody Team team) {
        try {
            teamService.updateTeam(team);
            return Result.success("更新团队成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }
}
