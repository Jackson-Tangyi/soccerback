package com.felix.soccerback.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.felix.soccerback.common.Result;
import com.felix.soccerback.entity.*;
import com.felix.soccerback.mapper.CureMapper;
import com.felix.soccerback.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

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
// CureChart 各月伤病
    @GetMapping("/treatment/month")
    public Result monthTreat(){
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

// CureChart 伤病分类
    @GetMapping("/treatment/type")
    public Result typeTreat(){
        QueryWrapper<Cure> feetQuery=new QueryWrapper<>();
        feetQuery.eq("place","Feet");
        int feetCount = (int)cureService.count(feetQuery);

        QueryWrapper<Cure> thighQuery=new QueryWrapper<>();
        thighQuery.eq("place","Thigh");
        int thighCount = (int)cureService.count(thighQuery);

        QueryWrapper<Cure> abdomenQuery=new QueryWrapper<>();
        abdomenQuery.eq("place","Abdomen");
        int abdomenCount = (int)cureService.count(abdomenQuery);

        QueryWrapper<Cure> armQuery=new QueryWrapper<>();
        armQuery.eq("place","Arm");
        int armCount = (int)cureService.count(armQuery);

        QueryWrapper<Cure> backQuery=new QueryWrapper<>();
        backQuery.eq("place","Back");
        int backCount = (int)cureService.count(backQuery);

        QueryWrapper<Cure> headQuery=new QueryWrapper<>();
        headQuery.eq("place","Head");
        int headCount = (int)cureService.count(headQuery);

        return Result.success(CollUtil.newArrayList(feetCount,thighCount,abdomenCount,armCount,backCount,headCount));
    }


//UserChart 用户注册
    @GetMapping("/create/user")
    public Result CreateCount() {
        List<User> list = userService.list();
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (User user : list) {
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }
//UserChart 性别比例
    @GetMapping("/count/user")
    public Result countMembers(){
        QueryWrapper<User> queryWrapper0=new QueryWrapper<User>();
        queryWrapper0.eq("sex","Female");

        QueryWrapper<User> queryWrapper1=new QueryWrapper<User>();
        queryWrapper1.eq("sex","Male");
        int count0 = (int) userService.count(queryWrapper0);
        int count1 = (int) userService.count(queryWrapper1);

        return Result.success(CollUtil.newArrayList(count0,count1));
    }

    //后台Home主页内容展示
//整个俱乐部人员组成
    @GetMapping("/whole/composition")
    public Result composition(){
        int playerCount = (int)playerService.count();
        int doctorCount = (int)doctorService.count();
        int coachCount = (int)coachService.count();

        return Result.success(CollUtil.newArrayList(playerCount,coachCount,doctorCount));
    }
//整个俱乐部的男女比例
    @GetMapping("/whole/sex")
    public Result wholeSex(){
        int totalMale,totalFemale;

        QueryWrapper<Coach> queryWrapper0=new QueryWrapper<Coach>();
        queryWrapper0.eq("sex","Female");
        QueryWrapper<Coach> queryWrapper1=new QueryWrapper<Coach>();
        queryWrapper1.eq("sex","Male");
        int coachCountFemale = (int) coachService.count(queryWrapper0);
        int coachCountMale = (int) coachService.count(queryWrapper1);

        QueryWrapper<Doctor> queryWrapper2=new QueryWrapper<Doctor>();
        queryWrapper0.eq("sex","Female");
        QueryWrapper<Doctor> queryWrapper3=new QueryWrapper<Doctor>();
        queryWrapper1.eq("sex","Male");
        int DoctorCountFemale = (int) coachService.count(queryWrapper0);
        int DoctorCountMale = (int) coachService.count(queryWrapper1);

        int playerCount = (int)playerService.count();

        totalMale=coachCountMale+DoctorCountMale+playerCount;
        totalFemale=coachCountFemale+DoctorCountFemale;

        return Result.success(CollUtil.newArrayList(totalFemale,totalMale));
    }


//PlayerChart 雷达图
    @GetMapping("/radar/{val}")
    public Result findRadar(@PathVariable Integer val) {
        Player player = playerService.getById(val);
        String name= player.getName();
        int speed= player.getSpeed();
        int power= player.getPower();
        int defence=player.getDefence();
        int dribbling=player.getDribbling();
        int pass= player.getPass();
        int shot=player.getShot();
        return Result.success(CollUtil.newArrayList(name,speed,power,defence,dribbling,pass,shot));
    }

//球员对比 雷达图
    @PostMapping("/compare")
    public Result Compare(@RequestBody List<Integer> ids) {
        List<Player> players = playerService.listByIds(ids);
        ArrayList<ArrayList<Object>> arrayList=new ArrayList<>();
        for (Player player : players) {
            String name= player.getName();
            int speed= player.getSpeed();
            int power= player.getPower();
            int defence=player.getDefence();
            int dribbling=player.getDribbling();
            int pass= player.getPass();
            int shot=player.getShot();
            arrayList.add(CollUtil.newArrayList(name,speed, power, defence, dribbling, pass, shot));
        }
        return Result.success(arrayList);
}
}
