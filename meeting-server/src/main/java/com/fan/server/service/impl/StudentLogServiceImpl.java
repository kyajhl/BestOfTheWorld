package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fan.server.pojo.Student;
import com.fan.server.pojo.StudentLog;
import com.fan.server.mapper.StudentLogMapper;
import com.fan.server.service.IStudentLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.server.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class StudentLogServiceImpl extends ServiceImpl<StudentLogMapper, StudentLog> implements IStudentLogService {

    @Autowired
    IStudentService studentService;

    @Override
    public void addStudentLog(String content, String mobilephone) throws Exception {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getMobilephone, mobilephone);
        if(Objects.isNull(studentService.getOne(wrapper)))
        {
            //为空，未找到学生
            throw new Exception("学生不存在");
        }
        StudentLog studentLog = new StudentLog();
        studentLog.setContent(content);
        studentLog.setStudentId(studentService.getStudentIdByMobilephone(mobilephone));
        studentLog.setLogDate(LocalDateTime.now());
        this.save(studentLog);
    }

    @Override
    public void updateStudentLog(StudentLog studentLog) throws Exception {
        LambdaQueryWrapper<StudentLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentLog::getId, studentLog.getId());
        if(Objects.isNull(this.getOne(wrapper)))
        {
            //为空，未找到该日志
            throw new Exception("该学生日志不存在");
        }
        this.updateById(studentLog);
    }

    @Override
    public void deleteStudentLog(StudentLog studentLog) {
        this.removeById(studentLog);
    }



}
