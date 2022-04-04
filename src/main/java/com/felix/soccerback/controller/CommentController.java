package com.felix.soccerback.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.felix.soccerback.common.Result;

import com.felix.soccerback.service.ICommentService;
import com.felix.soccerback.entity.Comment;
//相当于把一个user的MVC层改成模板，大部分都一样，小部分修改
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Felix
 * @since 2022-04-03
 */
@RestController

@RequestMapping("/comment")
public class CommentController {

    @Resource
    private ICommentService commentService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Comment comment) {
        if(comment.getId() == null){//新增评论
            comment.setUserId(TokenUtils.getCurrentUser().getId());//根据token将用户的id值传进去
            comment.setTime(DateUtil.now());//捕获当前评论时间
        }

        if(comment.getPid() !=null ){//说明是回复别人的留言
            Integer pid=comment.getPid();
            Comment pComment = commentService.getById(pid);//根据当前留言的pid获取父级留言
            if(pComment.getOriginId()!=null){//当前的父级留言是基于super留言，那么就设置相同的super留言
                comment.setOriginId(pComment.getOriginId());
            }else{//否则说明这个留言就是super留言
                comment.setOriginId(comment.getPid());
            }
        }

        commentService.saveOrUpdate(comment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        commentService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        commentService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(commentService.list());
    }

    @GetMapping("/tree/{newsId}")
    public Result findTree(@PathVariable Integer newsId) {
        List<Comment> newsComments =commentService.findCommentDetail(newsId);//根据当前的文章id查找它对应的评论和回复数据
        // 查询评论数据（不包括回复）
        List<Comment> originList = newsComments.stream().filter(comment -> comment.getOriginId() == null).collect(Collectors.toList());

//        替换写法   第一步，把查询出来的所有中是super评论的拿出来
//        List<Comment> originList = new ArrayList<>();
//        for (Comment newsComment : newsComments) {
//            if (newsComment.getOriginId() == null) {
//                originList.add(newsComment);
//            }
//        }

        // 设置评论数据的子节点，也就是回复内容
        for (Comment origin : originList) {
            List<Comment> comments = newsComments.stream().filter(comment -> origin.getId().equals(comment.getOriginId())).collect(Collectors.toList());  // 表示回复对象集合

//   替换写法         对每一个super留言，查询super留言下面的所有留言（不管是否是互相回复，反正最终都要显示在同一个super留言下面）

//            List<Comment> comments = new ArrayList<>();
//            for (Comment newsComment : newsComments) {
//                if (origin.getId().equals(newsComment.getOriginId())) {
//                    comments.add(newsComment);
//                }
//            }

            comments.forEach(comment -> {
//Optional 类是一个可以为null的容器对象。 如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。
//Optional 是个容器：它可以保存类型T的值，或者仅仅保存null。
                Optional<Comment> pComment = newsComments.stream().filter(c1 -> c1.getId().equals(comment.getPid())).findFirst();  // 找到当前评论的父级
                pComment.ifPresent((v -> {  // 找到父级评论的用户id和用户昵称，并设置给当前的回复对象
                    comment.setPUserId(v.getUserId());
                    comment.setPUsername(v.getUsername());
                }));
            });
//
// 替换写法           for (Comment comment : comments) {
//                // 找到当前评论的父级
//                Optional<Comment> pComment = Optional.empty();
//                for (Comment c1 : newsComments) {
//                    if (c1.getId().equals(comment.getPid())) {
//                        pComment = Optional.of(c1);
//                        break;
//                    }
//                }
//                pComment.ifPresent((v -> {  // 找到父级评论的用户id和用户昵称，并设置给当前的回复对象
//                    comment.setPUserId(v.getUserId());
//                    comment.setPNickname(v.getUsername());
//                }));
//            }

            origin.setChildren(comments);
        }

        return Result.success(originList);
    }


    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(commentService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(commentService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

