package com.felix.soccerback.mapper;

import com.felix.soccerback.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Felix
 * @since 2022-04-03
 */
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select c.*,u.username,u.avatar_url from club_comment c left join club_user u on c.user_id = u.id "  +
            "where c.news_id = #{newsId} order by id desc ")
    List<Comment> findCommentDetail(Integer newsId);
}
