package com.fan.server.mapper;

import com.fan.server.pojo.StudentAllInf;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentAllInfMapper {

    List<StudentAllInf> getStudentAllInf(Integer pageNo, Integer pageSize, String studentName);

    List<StudentAllInf> getStudentAllInfNoPage(String studentName);
}
