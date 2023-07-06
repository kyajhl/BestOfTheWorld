package com.fan.server.controller;


import com.fan.server.common.Result;
import com.fan.server.pojo.User;
import com.fan.server.service.IStudentService;
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
    @ApiOperation("用户登录接口")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> data = studentService.login(user);
        if (!Objects.isNull(data)) {
            return Result.success(data);
        }
        return Result.fail(202, "用户名或密码错误");
    }

    @GetMapping("/info")
    @ApiOperation("用户获取信息接口")
    public Result<Map<String, Object>> getInfo(@RequestParam String token) {
        Map<String, Object> data = studentService.getInfo(token);
        if (!Objects.isNull(data)) {
            return Result.success(data);
        }
        return Result.fail(203, "用户不存在");
    }

    @PostMapping("/addStudent")
    public Result<?> addStudent(@RequestBody Student student) {
        try {
            // 先查询是否有这个学生
            Student student1 = studentService.getStudent(student.getStudentId());
            if (student1 == null) {
                studentService.addStudent(student);
                return Result.success("添加学生成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }

}
