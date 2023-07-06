package com.fan.server.controller;


import com.fan.server.common.Result;
import com.fan.server.pojo.User;
import com.fan.server.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

}
