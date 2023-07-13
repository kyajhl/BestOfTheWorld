package com.fan.server.service;

import com.fan.server.pojo.StudentTeam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
public interface IStudentTeamService extends IService<StudentTeam> {

    void addStudentTeam(String mobilephone, String teamId, String position) throws Exception;

    void deleteStudentTeam(String mobilephone, String teamId) throws Exception;

    void updateStudentTeam(String teamName, Integer projectId, String teamId, List<String> selectedStudentList) throws Exception;

    Map<String, Object> getStudentListByTeamId(Long pageNo, Long pageSize, String teamId);

    void deleteStudentTeam(StudentTeam studentTeam);

    Map<String ,Object> getTeamInfById(String teamId);

    void deleteTeam(String teamId) throws Exception;

}
