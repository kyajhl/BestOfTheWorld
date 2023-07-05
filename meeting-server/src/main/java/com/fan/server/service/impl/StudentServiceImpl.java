package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fan.server.pojo.Student;
import com.fan.server.mapper.StudentMapper;
import com.fan.server.pojo.Teacher;
import com.fan.server.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.server.service.ITeacherService;
import com.fan.server.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Student getStudent(String studentId) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentId, studentId);
        Student data = this.getOne(wrapper);
        return data;
    }

    @Override
    public void addStudent(Student student) {
        this.save(student);
    }

    @Override
    public Map<String, Object> login(Student student) {
        // 查询学生是否存在
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentId, student.getMobilephone());
        // 根据登录用户传入的用户名，查询数据库里的用户
        Student dbUser = this.getOne(wrapper);
        // 判断是否非空
        if (!Objects.isNull(dbUser) && passwordEncoder.matches(student.getPassword(), dbUser.getPassword())) {
            // 设置 Jwt 中的密码为明文密码，因为要在个人信息中修改，必须要存密码
            dbUser.setPassword(student.getPassword());
            // 生成 Jwt
            String token = null;
            try {
                token = jwtUtil.createToken(dbUser, Student.class);
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
        // 解析 Jwt
        Claims claims = jwtUtil.parseToken(token);
        String id = claims.getId();

        Student student = jwtUtil.parseToken(token, Student.class);
        if (!Objects.isNull(student)) {
            HashMap<String, Object> data = new HashMap<>();
            // 根据 studentId 查询学生表
            LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Student::getStudentId, student.getStudentId());
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

}
