package com.fan.server.controller;

import com.fan.server.common.Result;
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
    private JwtUtil jwtUtil;

    @GetMapping("/info")
    public Result<Map<String, String>> getInfo(@RequestParam String token){
        // 解析 Jwt
        Claims claims = jwtUtil.parseToken(token);
        String id = claims.getId();
        String roleId =id.substring(id.length() - 1);
        HashMap<String, String> data = new HashMap<>();
        data.put("roleId", roleId);
        return Result.success(data);
    }
}
