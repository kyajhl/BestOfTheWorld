package com.fan.server.service.impl;

import com.fan.server.pojo.Comment;
import com.fan.server.mapper.CommentMapper;
import com.fan.server.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-04
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
