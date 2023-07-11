package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fan.server.pojo.StudentTeam;
import com.fan.server.mapper.StudentTeamMapper;
import com.fan.server.service.IStudentService;
import com.fan.server.service.IStudentTeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.server.service.ITeamService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentTeamServiceImpl extends ServiceImpl<StudentTeamMapper, StudentTeam> implements IStudentTeamService {

    @Autowired
    IStudentService studentService;

    @Autowired
    ITeamService teamService;

    @Override
    public void addStudentTeam(String mobilephone, String teamId, String position) throws Exception {
        StudentTeam studentTeam = new StudentTeam();
        Integer studentId;
        try {
            studentId = studentService.getStudentIdByMobilephone(mobilephone);
        }catch (Exception e){
            throw new Exception("学生不存在");
        }
        LambdaQueryWrapper<StudentTeam> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentTeam::getStudentId, studentId);
        wrapper.eq(StudentTeam::getTeamId, teamId);
        if(!Objects.isNull(this.getOne(wrapper))){
            //非空，查询到了
            throw new Exception("该学生已经在该团队了");
        }
        try{
            teamService.TeamNumberPlusOne(teamId);
        }catch (Exception e){
            throw new Exception("团队不存在");
        }
        studentTeam.setStudentId(studentId);
        studentTeam.setPosition(position);
        studentTeam.setTeamId(teamId);
        studentTeam.setGrade("0");
        this.save(studentTeam);
    }

    @Override
    public void deleteStudentTeam(String mobilephone, String teamId) throws Exception{
        StudentTeam studentTeam = new StudentTeam();
        Integer studentId;
        try {
            studentId = studentService.getStudentIdByMobilephone(mobilephone);
        }catch (Exception e){
            throw new Exception("学生不存在");
        }
        LambdaQueryWrapper<StudentTeam> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentTeam::getStudentId, studentId);
        wrapper.eq(StudentTeam::getTeamId, teamId);
        if(Objects.isNull(this.getOne(wrapper))){
            //为空，代表该学生不在该团队
            throw new Exception("学生不在该团队，无法删除");
        }
        try{
            teamService.TeamNumberSubOne(teamId);
        }catch (Exception e){
            throw new Exception("团队不存在");
        }
        this.remove(wrapper);
    }

}
