package com.fan.server.mapper;

import com.fan.server.pojo.TeamLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */

@Repository
public interface TeamLogMapper extends BaseMapper<TeamLog> {

    List<TeamLog> getTeamLogList(String studentId);

}
