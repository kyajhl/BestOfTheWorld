package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.server.pojo.Message;
import com.fan.server.mapper.MessageMapper;
import com.fan.server.pojo.Project;
import com.fan.server.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Override
    public void addMessage(Message message) {
        this.save(message);
    }

    @Override
    public Map<String, Object> getMessageList(Long pageNo, Long pageSize) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Message::getMessageDate);

        IPage<Message> page = new Page<>(pageNo, pageSize);
        this.page(page, wrapper);

        //封装 map
        HashMap<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("messageList", page.getRecords());

        return data;
    }

}
