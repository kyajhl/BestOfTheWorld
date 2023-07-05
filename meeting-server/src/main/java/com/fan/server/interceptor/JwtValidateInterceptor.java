package com.fan.server.interceptor;

import com.alibaba.fastjson.JSON;
import com.fan.server.common.Result;
import com.fan.server.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author KeFan
 * @date 2023/5/9
 * @time 18:42
 */

@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Access-Token");
        log.debug(request.getRequestURI() + "需要验证" + token);
        if (token != null) {
            try {
                jwtUtil.parseToken(token);
                log.debug(request.getRequestURI() + "验证通过");
                return true; // 放行
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("用户无效，请重新登录");
        log.debug(request.getRequestURI() + "验证失败，禁止访问");
        response.setContentType("application/json;charset=utf-8");
        Result<Object> fail = Result.fail(203, "用户无效，请重新登录");
        response.getWriter().write(JSON.toJSONString(fail));
        return false;   // 拦截
    }
}