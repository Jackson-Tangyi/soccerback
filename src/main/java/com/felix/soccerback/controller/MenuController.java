package com.felix.soccerback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.common.Constants;
import com.felix.soccerback.entity.Dict;
import com.felix.soccerback.mapper.DictMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.felix.soccerback.common.Result;

import com.felix.soccerback.service.IMenuService;
import com.felix.soccerback.entity.Menu;
//相当于把一个user的MVC层改成模板，大部分都一样，小部分修改
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Felix
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @Resource
    private DictMapper dictMapper;//所有的图标

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Menu menu) {
        menuService.saveOrUpdate(menu);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        menuService.removeById(id);
        return Result.success();
    }
    //获取所有的菜单项id展示到树形图
    @GetMapping("/ids")
    public Result findAllIds() {
        return Result.success(menuService.list().stream().map(Menu::getId));
    }

    @GetMapping//查询所有的菜单和子菜单
    public Result findAll(@RequestParam(defaultValue = "") String name) {
        return Result.success(menuService.findMenus(name));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        menuService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
    }


    @GetMapping("/icons")//把所有的dict表中type为icon的全部传回去
    public Result getIcons(){
        QueryWrapper<Dict> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(queryWrapper));
    }

}

