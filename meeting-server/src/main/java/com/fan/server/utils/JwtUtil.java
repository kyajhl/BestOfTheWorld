package com.fan.server.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author KeFan
 * @date 2023/5/9
 * @time 17:39
 */

@Component
public class JwtUtil {
    // 有效期
    private static final long time = 6 * 60 * 60 * 1000L;  // 30 分钟
//    private static final long time = 3000L;  // 3 s

    // 令牌秘钥
    private static final String secretKey = "*.&#%#%.[{666}].$%#&&.*";

    // 创建 token
    public String createToken(Object data) {
        // 当前时间
        long currentTime = System.currentTimeMillis();
        // 过期时间
        long expirationTime = currentTime + time;
        // 构建 jwt
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID() + "")
                .setSubject(JSON.toJSONString(data))
                .signWith(SignatureAlgorithm.HS256, encodeSecret(secretKey))
                .setExpiration(new Date(expirationTime));
        return builder.compact();
    }

    // 根据实体类的类型，创建 token
    public String createToken(Object obj, Class<?> clazz) throws Exception {
        String roleId = null;
        // 获取 roleId 的属性
        Field field = clazz.getDeclaredField("roleId");
        // 获取属性名
        String roleIdName = field.getName();
        // 获取属性值，通过get方法获取
        String getMethodName = "get" + roleIdName.substring(0, 1).toUpperCase() + roleIdName.substring(1);
        Method getMethod = obj.getClass().getMethod(getMethodName);
        Object value = getMethod.invoke(obj);
        // 给 roleId 赋值
        roleId = (String) value;

        // 当前时间
        long currentTime = System.currentTimeMillis();
        // 过期时间
        long expirationTime = currentTime + time;
        // 构建 jwt
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID() + "-" + roleId)
                .setSubject(JSON.toJSONString(obj))
                .signWith(SignatureAlgorithm.HS256, encodeSecret(secretKey))
                .setExpiration(new Date(expirationTime));
        return builder.compact();
    }

    // 根据 密钥 进行 加密，应该是进一步加密
    private SecretKey encodeSecret(String key) {
        byte[] encode = Base64.getEncoder().encode(key.getBytes());
        SecretKeySpec aes = new SecretKeySpec(encode, 0, encode.length, "AES");
        return aes;
    }

    // 解析 token
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(encodeSecret(secretKey))
                .parseClaimsJws(token)
                .getBody();
    }

    // 根据传入的类，解析成相应的 token
    public <T> T parseToken(String token, Class<T> classType) {
        Claims claims = Jwts.parser()
                .setSigningKey(encodeSecret(secretKey))
                .parseClaimsJws(token)
                .getBody();
        return JSON.parseObject(claims.getSubject(), classType);
    }
}
