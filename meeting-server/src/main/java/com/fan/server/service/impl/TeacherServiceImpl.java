package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fan.server.pojo.Student;
import com.fan.server.pojo.Teacher;
import com.fan.server.mapper.TeacherMapper;
import com.fan.server.pojo.User;
import com.fan.server.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.server.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void register(User user) throws Exception {
        // 判断 student 表是否存在 mobilePhone
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getMobilephone, user.getUsername());
        Teacher teacher = this.getOne(wrapper);
        if (!Objects.isNull(teacher)) {
            // 非空
            throw new Exception("手机号存在，注册失败，请重新注册");
        }
        // 空直接添加
        Teacher res = new Teacher();
        res.setMobilephone(user.getUsername());
        res.setPassword(passwordEncoder.encode(user.getPassword()));
        this.save(res);
    }

    @Override
    public Map<String, Object> login(User user) {
        // 查询教师是否存在
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getMobilephone, user.getUsername());
        // 根据登录用户传入的用户名，查询数据库里的用户
        Teacher dbUser = this.getOne(wrapper);
        // 判断是否非空
        if (!Objects.isNull(dbUser) && passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            // 设置 Jwt 中的密码为明文密码，因为要在个人信息中修改，必须要存密码
            dbUser.setPassword(user.getPassword());
            // 生成 Jwt
            String token = null;
            try {
                token = jwtUtil.createToken(dbUser, "2");
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
        Teacher teacher = jwtUtil.parseToken(token, Teacher.class);
        if (!Objects.isNull(teacher)) {
            HashMap<String, Object> data = new HashMap<>();
            // 根据 mobilePhone 查询教师表
            LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Teacher::getMobilephone, teacher.getMobilephone());
            // 从数据库里查询学生，不从 token 里直接拿，因为在登录之后
            // 学生信息有可能被修改
            Teacher modifyTeacher = this.getOne(wrapper);
            data.put("teacherName", modifyTeacher.getTeacherName());
            data.put("mobilephone", modifyTeacher.getMobilephone());
            data.put("password", teacher.getPassword());
            data.put("college", modifyTeacher.getCollege());
            data.put("avatar", modifyTeacher.getAvatar());

            return data;
        }
        return null;
    }

    @Override
    public Boolean updateInformation(Teacher teacher) throws Exception{
        // 根据 studentId 查询用户
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getMobilephone, teacher.getMobilephone());
        Teacher dbTeacher = this.getOne(wrapper);
        if(Objects.isNull(dbTeacher))
            throw new Exception("用户不存在");
        // 判断密码是否修改   true：密码匹配，未修改，    false：密码不匹配，修改了密码
        boolean isMatchPassword = passwordEncoder.matches(teacher.getPassword(), dbTeacher.getPassword());
        // 修改用户时，要把密码加密存到数据库
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        // 修改
        this.update(teacher, wrapper);
        // 返回
        return isMatchPassword;
    }

}
