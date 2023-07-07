package com.fan.server.service.impl;

import com.fan.server.service.IUserService;
import com.fan.server.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author KeFan
 * @date 2023/7/7
 * @time 14:44
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Map<String, String> getRoleId(String token) {
        // 解析 token
        Claims claims = jwtUtil.parseToken(token);
        String id = claims.getId();
        // 截取 roleId，为 id 的最后一位
        String roleId = id.substring(id.length() - 1);
        Map<String, String> data = new HashMap<>();
        data.put("roleId", roleId);
        return data;
    }
}
