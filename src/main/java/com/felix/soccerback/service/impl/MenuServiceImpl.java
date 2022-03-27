package com.felix.soccerback.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.felix.soccerback.entity.Menu;
import com.felix.soccerback.mapper.MenuMapper;
import com.felix.soccerback.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Felix
 * @since 2022-03-08
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findMenus(String name) {
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        if(StrUtil.isNotBlank(name)){
            queryWrapper.like("name",name);
        }
        //查询所有数据
        List<Menu> list=list(queryWrapper);

        //找出pid为null的一级菜单
        List<Menu> parentNodes=list.stream().filter(menu -> menu.getPid()==null).collect(Collectors.toList());
        //相当于
//        List<Menu> parentNodes = new ArrayList<>();
//        for (Menu menu : list) {
//            if (menu.getPid() == null) {
//                parentNodes.add(menu);
//            }
//        }
        for (Menu menu : parentNodes) {
            menu.setChildren(list().stream().filter(m->menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        //相当于
//            List<Menu> result = new ArrayList<>();
//            for (Menu m : list()) {
//                if (menu.getId().equals(m.getPid())) {
//                    result.add(m);
//                }
//            }
//            menu.setChildren(result);
        }

        return parentNodes;
    }
}
