package com.felix.soccerback.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.felix.soccerback.common.Result;
import com.felix.soccerback.entity.Cure;
import com.felix.soccerback.mapper.CureMapper;
import com.felix.soccerback.service.ICureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
