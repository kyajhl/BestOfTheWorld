package com.fan.server.controller;


import com.fan.server.common.Result;
import com.fan.server.pojo.StudentTeam;
import com.fan.server.service.IStudentTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/student-team")
public class StudentTeamController {

    @Autowired
    IStudentTeamService studentTeamService;

    @PostMapping("/addStudentTeam")
    @ApiOperation("为团队添加成员接口")
    public Result<?> addStudentTeam(@RequestParam String mobilephone,
                                    @RequestParam String teamId,
                                    @RequestParam String position) {
        try{
            studentTeamService.addStudentTeam(mobilephone, teamId, position);
            return Result.success("添加成员成功");
        }catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @DeleteMapping("/deleteStudentTeam")
    @ApiOperation("从团队删除学生")
    public Result<?> deleteStudentTeam(@RequestParam String mobilephone,
                                       @RequestParam String teamId) {
        try{
            studentTeamService.deleteStudentTeam(mobilephone, teamId);
            return Result.success("删除成员成功");
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }

}
