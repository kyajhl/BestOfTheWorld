package com.fan.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fan.server.pojo.Summarize;
import com.fan.server.mapper.SummarizeMapper;
import com.fan.server.service.ISummarizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
@Service
public class SummarizeServiceImpl extends ServiceImpl<SummarizeMapper, Summarize> implements ISummarizeService {

    @Override
    public void addSummarize(Summarize summarize) throws Exception {
        LambdaQueryWrapper<Summarize> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Summarize::getStudentId, summarize.getStudentId());
        wrapper.eq(Summarize::getTeamId, summarize.getTeamId());
        if(!Objects.isNull(this.getOne(wrapper))) {
            //非空，已经发表过总结了
            throw new Exception("已经发表过总结了");
        }
        this.save(summarize);
    }

    @Override
    public void updateSummarize(Summarize summarize) throws Exception {
        LambdaQueryWrapper<Summarize> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Summarize::getId, summarize.getId());
        if(Objects.isNull(this.getOne(wrapper))) {
            //为空，未找到该总结
            throw new Exception("无总结");
        }
        this.updateById(summarize);
    }

}
