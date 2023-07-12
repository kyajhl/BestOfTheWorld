package com.fan.server.service;

import com.fan.server.pojo.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
public interface IMessageService extends IService<Message> {

    void addMessage(Message message);

    Map<String, Object> getMessageList(Long pageNo, Long pageSize);

}
