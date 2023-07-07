package com.fan.server.service;

import java.util.Map;

/**
 * @author KeFan
 * @date 2023/7/7
 * @time 14:44
 */

public interface IUserService {

    Map<String, String> getRoleId(String token);
}