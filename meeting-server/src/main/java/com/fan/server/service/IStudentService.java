package com.fan.server.service;

import com.fan.server.pojo.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.server.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
public interface IStudentService extends IService<Student> {

    void register(User user) throws Exception;

}
