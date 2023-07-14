package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.server.pojo.Project;
import com.fan.server.pojo.Student;
import com.fan.server.pojo.StudentLog;
import com.fan.server.mapper.StudentLogMapper;
import com.fan.server.service.IStudentLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.server.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public Map<String, Object> getStudentLogList(Long pageNo, Long pageSize, Integer id){
        LambdaQueryWrapper<StudentLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id != null, StudentLog::getStudentId, id);
        wrapper.orderByAsc(StudentLog::getLogDate);
        IPage<StudentLog> page = new Page<>(pageNo, pageSize);
        this.page(page, wrapper);

        //封装 map
        HashMap<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("studentLogList", page.getRecords());

        return data;
    }

}
