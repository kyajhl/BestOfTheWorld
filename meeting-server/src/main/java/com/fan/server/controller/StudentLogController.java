package com.fan.server.controller;


import com.fan.server.common.Result;
import com.fan.server.pojo.StudentLog;
import com.fan.server.service.IStudentLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            return Result.success("更新学生日志成功");
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

    @GetMapping("/getStudentLogList")
    @ApiOperation("查询学生日志列表接口")
    public Result<Map<String, Object>> getStudentLogList(@RequestParam String mobilephone){
        try {
            Map<String, Object> data = studentLogService.getStudentLogList(mobilephone);
            return Result.success(data, "获取个人日志列表成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }

}
