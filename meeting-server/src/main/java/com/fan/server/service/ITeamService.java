package com.fan.server.service;

import com.fan.server.pojo.Project;
import com.fan.server.pojo.Team;
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
public interface ITeamService extends IService<Team> {

    void addTeam(String teamName, Integer projectId) throws Exception;

    void deleteTeam(String teamId) throws Exception;

    void updateTeam(Team team) throws Exception;

    Map<String, Object> getTeamList(Long pageNo, Long pageSize, String teamId, String teamName);

}
