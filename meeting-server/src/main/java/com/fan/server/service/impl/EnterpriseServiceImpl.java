package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fan.server.pojo.Enterprise;
import com.fan.server.mapper.EnterpriseMapper;
import com.fan.server.pojo.Student;
import com.fan.server.pojo.User;
import com.fan.server.service.IEnterpriseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.server.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
@Service
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise> implements IEnterpriseService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void register(User user) throws Exception {
        // 判断 student 表是否存在 mobilePhone
        LambdaQueryWrapper<Enterprise> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Enterprise::getMobilephone, user.getUsername());
        Enterprise enterprise = this.getOne(wrapper);
        if (!Objects.isNull(enterprise)) {
            // 非空
            throw new Exception("手机号存在，注册失败，请重新注册");
        }
        // 空直接添加
        Enterprise res = new Enterprise();
        res.setMobilephone(user.getUsername());
        res.setPassword(user.getPassword());
        this.save(res);
    }

    @Override
    public Map<String, Object> login(User user) {
        // 查询学生是否存在
        LambdaQueryWrapper<Enterprise> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Enterprise::getMobilephone, user.getUsername());
        // 根据登录用户传入的用户名，查询数据库里的用户
        Enterprise dbUser = this.getOne(wrapper);
        // 判断是否非空
        if (!Objects.isNull(dbUser) && passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            // 设置 Jwt 中的密码为明文密码，因为要在个人信息中修改，必须要存密码
            dbUser.setPassword(user.getPassword());
            // 生成 Jwt
            String token = null;
            try {
                token = jwtUtil.createToken(dbUser, "3");
                // 返回数据
                HashMap<String, Object> data = new HashMap<>();
                data.put("token", token);
                return data;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getInfo(String token) {
        Enterprise enterprise = jwtUtil.parseToken(token, Enterprise.class);
        if (!Objects.isNull(enterprise)) {
            HashMap<String, Object> data = new HashMap<>();
            // 根据 mobilePhone 查询企业表
            LambdaQueryWrapper<Enterprise> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Enterprise::getMobilephone, enterprise.getMobilephone());
            // 从数据库里查询学生，不从 token 里直接拿，因为在登录之后
            // 学生信息有可能被修改
            Enterprise modifyEnterprise = this.getOne(wrapper);
            data.put("enterpriseName", modifyEnterprise.getEnterpriseName());
            data.put("password", modifyEnterprise.getPassword());
            data.put("mobilephone", modifyEnterprise.getMobilephone());
            data.put("email", modifyEnterprise.getEmail());
            data.put("avatar", modifyEnterprise.getAvatar());

            return data;
        }
        return null;
    }

}
