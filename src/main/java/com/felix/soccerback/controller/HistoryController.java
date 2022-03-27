package com.felix.soccerback.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.felix.soccerback.common.Result;

import com.felix.soccerback.service.IHistoryService;
import com.felix.soccerback.entity.History;
//相当于把一个user的MVC层改成模板，大部分都一样，小部分修改
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Felix
 * @since 2022-03-25
 */
@RestController

@RequestMapping("/history")
public class HistoryController {

    @Resource
    private IHistoryService historyService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody History history) {
        historyService.saveOrUpdate(history);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        historyService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        historyService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/findAll")
    public Result findAll() {
        return Result.success(historyService.list());
    }


    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String content) {
        QueryWrapper<History> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("content",content);
        return Result.success(historyService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

