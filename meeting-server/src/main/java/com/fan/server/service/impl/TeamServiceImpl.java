package com.fan.server.service.impl;

import com.fan.server.pojo.Team;
import com.fan.server.mapper.TeamMapper;
import com.fan.server.service.ITeamService;
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
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements ITeamService {

}
