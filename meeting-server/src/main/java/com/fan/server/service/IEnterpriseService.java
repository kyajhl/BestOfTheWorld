package com.fan.server.service;

import com.fan.server.pojo.Enterprise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.server.pojo.User;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
public interface IEnterpriseService extends IService<Enterprise> {

    void register(User user) throws Exception;

    Map<String, Object> login(User user);

    Map<String, Object> getInfo(String token);

    Boolean updateInformation(Enterprise enterprise) throws Exception;
}
