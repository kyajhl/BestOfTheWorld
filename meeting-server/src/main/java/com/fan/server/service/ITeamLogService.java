package com.fan.server.service;

import com.fan.server.pojo.TeamLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
public interface ITeamLogService extends IService<TeamLog> {

    void addTeamLog(String content, String teamId) throws Exception;

    void updateTeamLog(TeamLog teamLog) throws Exception;

    void deleteTeamLog(TeamLog teamLog);

    Map<String, Object> getTeamLogList(Long pageNo, Long pageSize, LocalDate logDate);

}
