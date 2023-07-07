package com.fan.server.controller;

import com.fan.server.common.Result;
import com.fan.server.service.IUserService;
import com.fan.server.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/getRoleId")
    public Result<Map<String, String>> getRoleId(@RequestParam String token) {
        try {
            Map<String, String> data = userService.getRoleId(token);
            return Result.success(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }

    @PostMapping("/logout")
    public Result<?> logout() {
        try {
            return Result.success("退出成功，虽然这段文字不会显示，但还是要加上");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }
}
