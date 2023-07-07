package com.fan.server.controller;


import com.fan.server.common.Result;
import com.fan.server.pojo.Student;
import com.fan.server.pojo.Teacher;
import com.fan.server.pojo.User;
import com.fan.server.service.IStudentService;
import com.fan.server.service.ITeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    @PostMapping("/register")
    @ApiOperation("教师注册接口")
    public Result<?> register(@RequestBody User user) {
        try {
            teacherService.register(user);
            return Result.success("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }

    @PostMapping("/login")
    @ApiOperation("教师登录接口")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> data = teacherService.login(user);
        if (!Objects.isNull(data)) {
            return Result.success(data);
        }
        return Result.fail(202, "用户名或密码错误");
    }

    @GetMapping("/info")
    @ApiOperation("教师获取信息接口")
    public Result<Map<String, Object>> getInfo(@RequestParam String token) {
        Map<String, Object> data = teacherService.getInfo(token);
        if (!Objects.isNull(data)) {
            return Result.success(data);
        }
        return Result.fail(203, "教师不存在");
    }

    @PutMapping("/updateInformation")
    @ApiOperation("完善教师信息")
    public Result<Boolean>updateInformation(@RequestBody Teacher teacher){
        try {
            Boolean isMatchPassword = teacherService.updateInformation(teacher);
            if (isMatchPassword) return Result.success(true, "修改成功");
            else return Result.success(false, "密码已修改，请重新登录");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }

}
