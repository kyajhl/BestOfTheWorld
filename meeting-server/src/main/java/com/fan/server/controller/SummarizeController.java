package com.fan.server.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.fan.server.common.Result;
import com.fan.server.pojo.Summarize;
import com.fan.server.service.ISummarizeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
@RestController
@RequestMapping("/summarize")
public class SummarizeController {

    @Autowired
    ISummarizeService summarizeService;

    @PostMapping("/addSummarize")
    @ApiOperation("添加总结接口")
    public Result<?> addSummarize(@RequestBody Summarize summarize) {
        try{
            summarizeService.addSummarize(summarize);
            return Result.success("添加总结成功");
        }catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @PutMapping("/updateSummarize")
    @ApiOperation("更新总结接口")
    public Result<?> updateSummarize(@RequestBody Summarize summarize) {
        try{
            summarizeService.updateSummarize(summarize);
            return Result.success("更新总结成功");
        }catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }


}
