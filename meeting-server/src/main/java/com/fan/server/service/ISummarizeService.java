package com.fan.server.service;

import com.fan.server.pojo.Summarize;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.concurrent.ExecutionException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-05
 */
public interface ISummarizeService extends IService<Summarize> {

    void addSummarize(Summarize summarize) throws Exception;

    void updateSummarize(Summarize summarize) throws Exception;

}
