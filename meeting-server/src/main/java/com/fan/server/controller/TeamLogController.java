package com.fan.server.controller;


import com.fan.server.common.Result;
import com.fan.server.pojo.StudentLog;
import com.fan.server.pojo.TeamLog;
import com.fan.server.service.IStudentLogService;
import com.fan.server.service.ITeamLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
@RestController
@RequestMapping("/team-log")
public class TeamLogController {

    @Autowired
    ITeamLogService teamLogService;

    @PostMapping("/addTeamLog")
    @ApiOperation("添加团队日志接口")
    public Result<?> addTeamLog(@RequestParam String content,
                                   @RequestParam String teamId){
        try{
            teamLogService.addTeamLog(content, teamId);
            return Result.success("添加团队日志成功");
        }catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @PutMapping("/updateTeamLog")
    @ApiOperation("更新团队日志接口")
    public Result<?> updateTeamLog(@RequestBody TeamLog teamLog) {
        try{
            teamLogService.updateTeamLog(teamLog);
            return Result.success("添加团队日志成功");
        }catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @DeleteMapping("/deleteTeamLog")
    @ApiOperation("删除团队日志接口")
    public Result<?> deleteTeamLog(@RequestBody TeamLog teamLog) {
        try{
            teamLogService.deleteTeamLog(teamLog);
            return Result.success("删除团队日志成功");
        }catch (Exception e) {
            return Result.fail("删除失败");
        }
    }

    @GetMapping("/getTeamLogList")
    @ApiOperation("查询团队日志接口")
    public Result<Map<String, Object>> getTeamLogList(@RequestParam String studentId){
        try {
            Map<String, Object> data = teamLogService.getTeamLogList(studentId);
            return Result.success(data, "获取团队日志列表成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }

}
