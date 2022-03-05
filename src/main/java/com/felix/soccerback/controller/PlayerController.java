package com.felix.soccerback.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.User;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.felix.soccerback.common.Result;

import com.felix.soccerback.service.IPlayerService;
import com.felix.soccerback.entity.Player;
//相当于把一个user的MVC层改成模板，大部分都一样，小部分修改
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Felix
 * @since 2022-02-28
 */
@RestController

@RequestMapping("/player")
public class PlayerController {

    @Resource
    private IPlayerService playerService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Player player) {
        playerService.saveOrUpdate(player);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        playerService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        playerService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(playerService.list());
    }

    //给多选框使用
    @GetMapping("/findPlayers")
    public Result findCoaches() {
        return Result.success(playerService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(playerService.getById(id));
    }


    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        return Result.success(playerService.findPage(new Page<>(pageNum, pageSize), name));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询所有数据
        List<Player> list=playerService.list();
        //通过工具类创建writer 写到磁盘路径
//        ExcelWriter writer= ExcelUtil.getWriter(filesUploadPath+"/用户信息.xlsx")；
        //在内存操作，写出到浏览器
        ExcelWriter writer= ExcelUtil.getWriter(true);

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);
        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("Player_Table","UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();

    }

    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception{
        InputStream inputStream=file.getInputStream();
        ExcelReader reader=ExcelUtil.getReader(inputStream);
//      方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Player> list = reader.readAll(Player.class);

        playerService.saveBatch(list);
        return Result.success();
    }

}

