package com.fan.server.service;

import com.fan.server.pojo.StudentLog;
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
public interface IStudentLogService extends IService<StudentLog> {

    void addStudentLog(String content, String mobilephone) throws Exception;

    void updateStudentLog(StudentLog studentLog) throws Exception;

    void deleteStudentLog(StudentLog studentLog);

    Map<String, Object> getStudentLogList(String mobilephone);

}
