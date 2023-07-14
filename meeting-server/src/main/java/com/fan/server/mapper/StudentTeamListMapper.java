package com.fan.server.mapper;

import com.fan.server.pojo.StudentTeamList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentTeamListMapper {

    List<StudentTeamList> getTeamListByMobilephone(Integer pageNo, Integer pageSize, Integer id, String teamName);

}
