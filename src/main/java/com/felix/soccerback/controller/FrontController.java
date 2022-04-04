package com.felix.soccerback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.felix.soccerback.common.Result;
import com.felix.soccerback.entity.Files;
import com.felix.soccerback.mapper.FileMapper;
import com.felix.soccerback.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController

@RequestMapping("/front")
public class FrontController {

    @Resource
    private IAchievementService achievementService;

    @Resource
    private IPlayerService playerService;

    @Resource
    private ICoachService coachService;

    @Resource
    private IHistoryService historyService;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private IGameService gameService;

    //成就展示界面
    @GetMapping("/findAllCups")
    public Result findAllCups(){
        return Result.success(achievementService.list());
    }

    //球员展示界面
    @GetMapping("/findPlayers")
    public Result findPlayers() {
        return Result.success(playerService.list());
    }

    //教练展示界面
    @GetMapping("/findCoaches")
    public Result findCoaches() {
        return Result.success(coachService.list());
    }

    //历史展示界面
    @GetMapping("/findAll")
    public Result findAll() {
        return Result.success(historyService.list());
    }


    // 前台界面获取球队的照片
    @GetMapping("/pic")
    public Result findPic(){
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","jpg");
        queryWrapper.like("name","home");
        queryWrapper.eq("is_delete",0);
        return Result.success(fileMapper.selectList(queryWrapper));
    }

    // 前台获取视频文件
    @GetMapping("/videos")
    public Result findVideos(){
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","MP4");
//        or().eq("type","mp4")
        queryWrapper.eq("enable",1);//当使用enable为1，前端才展示
        return Result.success(fileMapper.selectList(queryWrapper));
    }
    // 根据id查询视频 video details
    @GetMapping("/video/{id}")
    public Result findVideoById(@PathVariable Integer id, HttpServletResponse response) {
        Files video = fileMapper.selectById(id);
//        response.setHeader("Accept-Ranges", "bytes");
//        response.setHeader("Content-Length", String.valueOf(video.getSize()));
        return Result.success(video);
    }
    //前台界面比赛信息展示
    @GetMapping("/findAllGames")
    public Result findAllGames() {
        return Result.success(gameService.list());
    }


}
