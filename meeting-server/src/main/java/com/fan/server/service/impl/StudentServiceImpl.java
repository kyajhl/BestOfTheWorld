package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fan.server.pojo.Student;
import com.fan.server.mapper.StudentMapper;
import com.fan.server.pojo.User;
import com.fan.server.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
