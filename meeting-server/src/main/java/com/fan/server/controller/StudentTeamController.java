package com.fan.server.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.fan.server.common.Result;
import com.fan.server.pojo.StudentTeam;
import com.fan.server.service.IStudentTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.description.field.FieldDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
@RequestMapping("/student-team")
public class StudentTeamController {

    @Autowired
    IStudentTeamService studentTeamService;

    @PostMapping("/addStudentTeam")
    @ApiOperation("为团队添加成员接口")
    public Result<?> addStudentTeam(@RequestParam String mobilephone,
                                    @RequestParam String teamId,
                                    @RequestParam String position) {
        try {
            studentTeamService.addStudentTeam(mobilephone, teamId, position);
            return Result.success("添加成员成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @DeleteMapping("/deleteStudentTeam")
    @ApiOperation("从团队删除学生接口")
    public Result<?> deleteStudentTeam(@RequestParam String mobilephone,
                                       @RequestParam String teamId) {
        try {
            studentTeamService.deleteStudentTeam(mobilephone, teamId);
            return Result.success("删除成员成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @PutMapping("/updateStudentTeam")
    @ApiOperation("更新学生在团队中的信息接口")
    public Result<?> updateStudentTeam(
            @RequestParam(value = "teamName") String teamName,
            @RequestParam(value = "projectId") Integer projectId,
            @RequestParam(value = "teamId") String teamId,
            @RequestParam(value = "selectedStudentList") List<String> selectedStudentList
    ) {
        try {
            studentTeamService.updateStudentTeam(teamName, projectId, teamId, selectedStudentList);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/getStudentListByTeamId")
    @ApiOperation("通过团队id查询成员接口")
    public Result<Map<String, Object>> getStudentListByTeamId(
            @RequestParam("pageNo") Long pageNo,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam(required = false, value = "teamId") String teamId) {
        try {
            Map<String, Object> data = studentTeamService.getStudentListByTeamId(pageNo, pageSize, teamId);
            return Result.success(data, "获取成员成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/getTeamInfById")
    @ApiOperation("获取团队所有信息接口")
    public Result<Map<String, Object>> getTeamInfById(@RequestParam String teamId) {
        try {
            Map<String, Object> data = studentTeamService.getTeamInfById(teamId);
            /*
            * teamName
            * projectId
            * volume
            * selectedStudentList
            * */
            return Result.success(data, "获取团队信息成功");
        }catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @DeleteMapping("/deleteTeam")
    @ApiOperation("删除整个团队接口")
    public Result<?> deleteTeam(@RequestParam String teamId) {
        try {
            studentTeamService.deleteTeam(teamId);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/getTeamInformation")
    @ApiOperation("获取团队信息和成员所有信息接口")
    public Result<?> getTeamInformation(@RequestParam String teamId) {
        try {
            Map<String, Object> data = studentTeamService.getTeamInformation(teamId);
            /*
             * teamName
             * projectId
             * projectName
             * studentList
             * */
            return Result.success(data, "获取团队信息成功");
        }catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/getTeamListByMobilephone")
    @ApiOperation("获取该学生选择的团队列表")
    public Result<Map<String, Object>> getTeamListByMobilephone(
            @RequestParam("pageNo") Long pageNo,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam(required = false, value = "mobilephone") String mobilephone,
            @RequestParam(required = false, value = "teamName") String teamName) {
        try{
            /*
                项目名
                团队名
                团队人数
                团队Id
             */
            Map<String, Object> data = studentTeamService.getTeamListByMobilephone(pageNo, pageSize, mobilephone, teamName);
            return Result.success(data, "获取学生选择的团队列表");
        }catch (Exception e) {
            return Result.fail(e.getMessage());
        }

    }

    @GetMapping("/getPositionByTeamId")
    @ApiOperation("根据 teamId 获取对应的职位")
    public Result<Map<String, String>> getPositionByTeamId(@RequestParam String teamId, @RequestParam Integer studentId) {
        try {
            Map<String, String> data = studentTeamService.getPositionByTeamId(teamId, studentId);
            return Result.success(data, "获取对应的职位成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }

    @GetMapping("/getStudentAllInf")
    @ApiOperation("获得学生团队所有信息接口")
    public Result<Map<String, Object>> getStudentAllInf(
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("studentName") String studentName
    ) {
        try{
            Map<String, Object> data = studentTeamService.getStudentAllInf(pageNo, pageSize, studentName);
            return Result.success(data, "获取学生所有信息列表成功");
        }catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }


}
