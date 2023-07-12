package com.fan.server.controller;


import com.fan.server.common.Result;
import com.fan.server.pojo.User;
import com.fan.server.service.IStudentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fan.server.pojo.Student;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @PostMapping("/register")
    @ApiOperation("学生注册接口")
    public Result<?> register(@RequestBody User user) {
        try {
            studentService.register(user);
            return Result.success("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }

    @PostMapping("/login")
    @ApiOperation("学生登录接口")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> data = studentService.login(user);
        if (!Objects.isNull(data)) {
            return Result.success(data);
        }
        return Result.fail(202, "用户名或密码错误");
    }

    @GetMapping("/info")
    @ApiOperation("学生获取信息接口")
    public Result<Map<String, Object>> getInfo(@RequestParam String token) {
        Map<String, Object> data = studentService.getInfo(token);
        if (!Objects.isNull(data)) {
            return Result.success(data);
        }
        return Result.fail(203, "学生不存在");
    }

    @PutMapping("/updateInformation")
    @ApiOperation("完善学生信息")
    public Result<Boolean> updateInformation(@RequestBody Student student){
        try {
            Boolean isMatchPassword = studentService.updateInformation(student);
            if (isMatchPassword) return Result.success(true, "修改成功");
            else return Result.success(false, "密码已修改，请重新登录");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }

    @GetMapping("/getSudentNameByMobilephone")
    @ApiOperation("通过手机号查询学生姓名接口")
    public Result<?> getStudentNameByMobilephone(@RequestParam String mobilephone) {
        try {
            String studentName = studentService.getStudentNameByMobilephone(mobilephone);
            return Result.success(studentName);
        }catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

}
