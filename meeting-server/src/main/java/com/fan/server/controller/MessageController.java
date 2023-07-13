package com.fan.server.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.fan.server.common.Result;
import com.fan.server.pojo.Message;
import com.fan.server.service.IMessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    IMessageService messageService;

    @PostMapping("/addMessage")
    @ApiOperation("添加留言接口")
    public Result<?> addMessage(@RequestBody Message message) {
        try {
            messageService.addMessage(message);
            return Result.success("添加留言成功");
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/getMessageList")
    @ApiOperation("获取留言列表接口")
    public Result<Map<String, Object>> getMessageList(){
        try {
            Map<String, Object> data = messageService.getMessageList();
            return Result.success(data, "获取留言列表成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(202);
    }

}
