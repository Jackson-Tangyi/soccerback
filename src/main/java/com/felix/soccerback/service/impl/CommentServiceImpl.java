package com.felix.soccerback.service.impl;

import com.felix.soccerback.entity.Comment;
import com.felix.soccerback.mapper.CommentMapper;
import com.felix.soccerback.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Felix
 * @since 2022-04-03
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findCommentDetail(Integer newsId) {
        return commentMapper.findCommentDetail(newsId);
    }
}
