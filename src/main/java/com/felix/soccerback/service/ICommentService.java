package com.felix.soccerback.service;

import com.felix.soccerback.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Felix
 * @since 2022-04-03
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> findCommentDetail(Integer newsId);
}
