package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fan.server.pojo.Student;
import com.fan.server.mapper.StudentMapper;
import com.fan.server.pojo.User;
import com.fan.server.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.server.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void register(User user) throws Exception {
        // 判断 student 表是否存在 mobilePhone
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getMobilephone, user.getUsername());
        Student student = this.getOne(wrapper);
        if (!Objects.isNull(student)) {
            // 非空
            throw new Exception("手机号存在，注册失败，请重新注册");
        }
        // 空直接添加
        Student res = new Student();
        res.setMobilephone(user.getUsername());
        res.setPassword(user.getPassword());
        this.save(res);
    }

    @Override
    public Map<String, Object> login(User user) {
        // 查询学生是否存在
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getMobilephone, user.getUsername());
        // 根据登录用户传入的用户名，查询数据库里的用户
        Student dbUser = this.getOne(wrapper);
        // 判断是否非空
        if (!Objects.isNull(dbUser) && passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            // 设置 Jwt 中的密码为明文密码，因为要在个人信息中修改，必须要存密码
            dbUser.setPassword(user.getPassword());
            // 生成 Jwt
            String token = null;
            try {
                token = jwtUtil.createToken(dbUser, "1");
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
        Student student = jwtUtil.parseToken(token, Student.class);
        if (!Objects.isNull(student)) {
            HashMap<String, Object> data = new HashMap<>();
            // 根据 studentId 查询学生表
            LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Student::getMobilephone, student.getMobilephone());
            // 从数据库里查询学生，不从 token 里直接拿，因为在登录之后
            // 学生信息有可能被修改
            Student modifyStudent = this.getOne(wrapper);
            data.put("studentId", modifyStudent.getStudentId());
            data.put("studentName", modifyStudent.getStudentName());
            data.put("mobilephone", modifyStudent.getMobilephone());
            data.put("password", student.getPassword());
            data.put("gender", modifyStudent.getGender());
            if (modifyStudent.getBirthday() != null) {
                data.put("birthday", modifyStudent.getBirthday().toString());
            } else {
                data.put("birthday", null);
            }
            data.put("college", modifyStudent.getCollege());
            data.put("major", modifyStudent.getMajor());
            data.put("majorClass", modifyStudent.getMajorClass());
            data.put("avatar", modifyStudent.getAvatar());

            return data;
        }
        return null;
    }

    @Override
    public Boolean updateInformation(Student student) {
        // 根据 studentId 查询用户
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getMobilephone, student.getMobilephone());
        Student dbStudent = this.getOne(wrapper);
        // 判断密码是否修改   true：密码匹配，未修改，    false：密码不匹配，修改了密码
        boolean isMatchPassword = passwordEncoder.matches(student.getPassword(), dbStudent.getPassword());
        // 修改用户时，要把密码加密存到数据库
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        // 要把生日 + 1 天，才能正确对应
        LocalDate birthday = student.getBirthday();
        LocalDate correctBirthday = birthday.plusDays(1);
        student.setBirthday(correctBirthday);
        // 修改
        this.update(student, wrapper);
        // 返回
        return isMatchPassword;
    }

}
