package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.server.pojo.*;
import com.fan.server.mapper.TeamLogMapper;
import com.fan.server.service.ITeamLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.server.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class TeamLogServiceImpl extends ServiceImpl<TeamLogMapper, TeamLog> implements ITeamLogService {

    @Autowired
    ITeamService teamService;

    @Autowired
    private TeamLogMapper teamLogMapper;

    @Override
    public void addTeamLog(String content, String teamId) throws Exception {
        LambdaQueryWrapper<Team> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Team::getTeamId, teamId);
        if(Objects.isNull(teamService.getOne(wrapper)))
        {
            //为空，未找到学生
            throw new Exception("团队不存在");
        }
        TeamLog teamLog = new TeamLog();
        teamLog.setContent(content);
        teamLog.setTeamId(teamId);
        teamLog.setLogDate(LocalDateTime.now());
        this.save(teamLog);
    }

    @Override
    public void updateTeamLog(TeamLog teamLog) throws Exception {
        LambdaQueryWrapper<TeamLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(teamLog.getId() != null, TeamLog::getId, teamLog.getId());
        if(Objects.isNull(this.getOne(wrapper)))
        {
            //为空，未找到该日志
            throw new Exception("该团队日志不存在");
        }
        if (teamLog.getComment() == null) {
            teamLog.setLogDate(LocalDateTime.now());
        } else {
            teamLog.setLogDate(teamLog.getLogDate().plusHours(8));
        }
        this.updateById(teamLog);
    }

    @Override
    public void deleteTeamLog(TeamLog teamLog) {
        this.removeById(teamLog);
    }

    @Override
    public Map<String, Object> getTeamLogList(String studentId) {
        // 写 sql 查询
        List<TeamLog> teamLogsList = teamLogMapper.getTeamLogList(studentId);

        //封装 map
        HashMap<String, Object> data = new HashMap<>();
        data.put("teamLogsList", teamLogsList);

        return data;
    }


}
