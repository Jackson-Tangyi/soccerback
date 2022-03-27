package com.felix.soccerback.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.felix.soccerback.common.Result;

import com.felix.soccerback.service.IAchievementService;
import com.felix.soccerback.entity.Achievement;
//相当于把一个user的MVC层改成模板，大部分都一样，小部分修改
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Felix
 * @since 2022-03-26
 */
@RestController

@RequestMapping("/achievement")
public class AchievementController {

    @Resource
    private IAchievementService achievementService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Achievement achievement) {
        achievementService.saveOrUpdate(achievement);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        achievementService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        achievementService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(achievementService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(achievementService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String name) {
        QueryWrapper<Achievement> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        return Result.success(achievementService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

