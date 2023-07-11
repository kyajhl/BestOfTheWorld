package com.fan.server.controller;


import com.fan.server.common.Result;
import com.fan.server.pojo.StudentLog;
import com.fan.server.service.IStudentLogService;
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
@RequestMapping("/student-log")
public class StudentLogController {

    @Autowired
    IStudentLogService studentLogService;

    @PostMapping("/addStudentLog")
    @ApiOperation("添加学生日志接口")
    public Result<?> addStudentLog(@RequestParam String content,
                                   @RequestParam String mobilephone){
        try{
            studentLogService.addStudentLog(content, mobilephone);
            return Result.success("添加学生日志成功");
        }catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @PutMapping("/updateStudentLog")
    @ApiOperation("更新学生日志接口")
    public Result<?> updateStudentLog(@RequestBody StudentLog studentLog) {
        try{
            studentLogService.updateStudentLog(studentLog);
            return Result.success("添加学生日志成功");
        }catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @DeleteMapping("/deleteStudentLog")
    @ApiOperation("删除学生日志接口")
    public Result<?> deleteStudentLog(@RequestBody StudentLog studentLog) {
        try{
            studentLogService.deleteStudentLog(studentLog);
            return Result.success("删除学生日志成功");
        }catch (Exception e) {
            return Result.fail("删除失败");
        }
    }

}
