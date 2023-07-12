package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.server.pojo.Project;
import com.fan.server.pojo.StudentTeam;
import com.fan.server.mapper.StudentTeamMapper;
import com.fan.server.pojo.Team;
import com.fan.server.service.IStudentService;
import com.fan.server.service.IStudentTeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.server.service.ITeamService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void deleteStudentTeam(StudentTeam studentTeam) {
        removeById(studentTeam.getId());
    }

    @Override
    public void updateStudentTeam(String teamName, Integer projectId, String teamId, List<String> selectedStudentList) throws Exception {
        LambdaQueryWrapper<StudentTeam> studentTeamWrapper = new LambdaQueryWrapper<>();
        studentTeamWrapper.eq(StudentTeam::getTeamId, teamId);
        List<StudentTeam> preList = this.list(studentTeamWrapper);
        preList.forEach(this::deleteStudentTeam);
        Team team = new Team();
        team.setTeamId(teamId);
        team.setTeamNumber(0);
        team.setTeamName(teamName);
        team.setProjectId(projectId);
        teamService.updateTeam(team);
        selectedStudentList.forEach((value) -> {
            String[] nowString = value.split("-");
            try {
                addStudentTeam(nowString[0], teamId, nowString[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Map<String, Object> getStudentListByTeamId(Long pageNo, Long pageSize, String teamId) {
        LambdaQueryWrapper<StudentTeam> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentTeam::getTeamId, teamId);
        wrapper.orderByAsc(StudentTeam::getStudentId);

        IPage<StudentTeam> page = new Page<>(pageNo, pageSize);
        this.page(page, wrapper);

        //封装 map
        HashMap<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("projectList", page.getRecords());

        return data;
    }

}
