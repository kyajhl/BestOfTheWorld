package com.fan.server.service;

import com.fan.server.pojo.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
public interface IStudentService extends IService<Student> {

    Map<String, Object> login(Student student);

    Map<String, Object> getInfo(String token);

    void addStudent(Student student);

    Student getStudent(String studentId);

}
