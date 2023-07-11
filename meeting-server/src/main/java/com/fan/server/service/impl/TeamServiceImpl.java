package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.server.pojo.Project;
import com.fan.server.pojo.Team;
import com.fan.server.mapper.TeamMapper;
import com.fan.server.service.IProjectService;
import com.fan.server.service.ITeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements ITeamService {

    @Autowired
    private IProjectService projectService;

    @Override
    public void addTeam(String teamName, Integer projectId) throws Exception{
        try{
            Project project = projectService.getProjectById(projectId);
            if(Objects.isNull(project))
                throw new Exception("项目不存在");
            Team team = new Team();
            team.setTeamId(UUID.randomUUID().toString());
            team.setTeamName(teamName);
            team.setProjectId(projectId);
            team.setTeamNumber(0);
            this.save(team);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void deleteTeam(String teamId) throws Exception {
        LambdaQueryWrapper<Team> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Team::getTeamId, teamId);
        Team team1 = this.getOne(wrapper);
        if(Objects.isNull(team1)) {
            //未找到项目
            throw  new Exception(("团队不存在"));
        }
        this.remove(wrapper);
    }

    @Override
    public void updateTeam(Team team) throws Exception {
        LambdaQueryWrapper<Team> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Team::getTeamId, team.getTeamId());
        Team team1 = this.getOne(wrapper);
        if(Objects.isNull(team1)) {
            //未找到团队
            throw new Exception(("团队不存在"));
        }
        this.update(team, wrapper);
    }

    @Override
    public Map<String, Object> getTeamList(Long pageNo, Long pageSize, String teamId, String teamName){
        LambdaQueryWrapper<Team> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Team::getTeamId, teamId);
        wrapper.like(Team::getTeamName, teamName);

        IPage<Team> page = new Page<>(pageNo, pageSize);
        this.page(page, wrapper);

        //封装map
        HashMap<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("teamList", page.getRecords());

        return data;
    }

    @Override
    public void TeamNumberPlusOne(String teamId) throws Exception{
        LambdaQueryWrapper<Team> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Team::getTeamId, teamId);
        Team team = this.getOne(wrapper);
        if(Objects.isNull(team))
            throw new Exception("团队不存在");
        team.setTeamNumber(team.getTeamNumber() + 1);
        this.updateById(team);
    }

    @Override
    public void TeamNumberSubOne(String teamId) throws Exception{
        LambdaQueryWrapper<Team> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Team::getTeamId, teamId);
        Team team = this.getOne(wrapper);
        if(Objects.isNull(team))
            throw new Exception("团队不存在");
        team.setTeamNumber(team.getTeamNumber() - 1);
        this.updateById(team);
    }

}
