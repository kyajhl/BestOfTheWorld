package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.server.pojo.Message;
import com.fan.server.mapper.MessageMapper;
import com.fan.server.pojo.MessageAndName;
import com.fan.server.pojo.Project;
import com.fan.server.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.server.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    IStudentService studentService;

    @Override
    public void addMessage(Message message) {
        this.save(message);
    }

    @Override
    public Map<String, Object> getMessageListByPage(Long pageNo, Long pageSize) {
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

    @Override
    public Map<String, Object> getMessageList() {
        Map<String, Object> data = new HashMap<>();
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Message::getMessageDate);
        List<Message> list =  this.list(wrapper);
        List<MessageAndName> list1 = new ArrayList<>();
        list.forEach((value) -> {
            MessageAndName now = new MessageAndName();
            now.setMessageInf(value);
            try {
                now.setStudentName(studentService.getStudentNameByPhone(value.getMobilephone()));
                list1.add(now);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        data.put("messageList", list1);
        return data;
    }

}
