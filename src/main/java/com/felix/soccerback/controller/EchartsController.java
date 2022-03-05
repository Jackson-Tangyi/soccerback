package com.felix.soccerback.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.felix.soccerback.common.Result;
import com.felix.soccerback.entity.Cure;
import com.felix.soccerback.entity.Player;
import com.felix.soccerback.mapper.CureMapper;
import com.felix.soccerback.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    private ICureService cureService;
    
    @Resource
    private IPlayerService playerService;

    @Resource
    private ICoachService coachService;

    @Resource
    private IDirectorService directorService;

    @Resource
    private IDoctorService doctorService;

    @Resource
    private IUserService userService;

    @GetMapping("/treatment")
    public Result treat(){
        List<Cure> list=cureService.list();
        int arr[]=new int[12];
        for (int i = 0; i <arr.length ; i++) {
            arr[i]=0;
        }
        for (Cure c:list) {
            Date createTime=c.getStart();
            int month = DateUtil.month(createTime);
            arr[month]+=1;
        }
        return Result.success(arr);
    }

    @GetMapping("/members")
    public Result members(){

        long c1 = playerService.count();
        long c2 = coachService.count();
        long c3 = doctorService.count();
        long c4 = directorService.count();
        long c5 = userService.count();

        return Result.success();
    }

    @GetMapping("/radar/{val}")
    public Result findRadar(@PathVariable Integer val) {
        Player player = playerService.getById(val);
        int speed= player.getSpeed();
        int power= player.getPower();
        int defence=player.getDefence();
        int dribbling=player.getDribbling();
        int pass= player.getPass();
        int shot=player.getShot();
        return Result.success(CollUtil.newArrayList(speed,power,defence,dribbling,pass,shot));
    }
}
