package com.fan.server.service.impl;

import com.fan.server.pojo.Student;
import com.fan.server.mapper.StudentMapper;
import com.fan.server.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-04
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
