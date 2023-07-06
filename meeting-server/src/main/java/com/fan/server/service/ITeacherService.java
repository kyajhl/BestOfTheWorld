package com.fan.server.service;

import com.fan.server.pojo.Student;
import com.fan.server.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.server.pojo.User;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
public interface ITeacherService extends IService<Teacher> {

    void register(User user) throws Exception;

    Map<String, Object> login(Teacher teacher);

    Map<String, Object> getInfo(String token);

    void addTeacher(Teacher teacher);

    Teacher getTeacher(String mobilephone);

}
