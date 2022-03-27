package com.felix.soccerback.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.felix.soccerback.entity.Menu;
import com.felix.soccerback.entity.Role;
import com.felix.soccerback.entity.RoleMenu;
import com.felix.soccerback.mapper.RoleMapper;
import com.felix.soccerback.mapper.RoleMenuMapper;
import com.felix.soccerback.service.IMenuService;
import com.felix.soccerback.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Felix
 * @since 2022-03-08
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;
    //保持事务一致性（同时成功或者同时失败）
    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        //删除当前角色id所有的绑定关系
        roleMenuMapper.deleteByRoleId(roleId);

        //把前端传过来的菜单id数组绑定到这个角色id上去
        List<Integer> menuIdsCopy= CollUtil.newArrayList(menuIds);
        for (Integer menuId : menuIds) {
            Menu menu=menuService.getById(menuId);
            if(menu.getPid()!=null && !menuIdsCopy.contains(menu.getId())){// 二级菜单 并且传过来的menuId数组里面没有它的父级id
                //那么我们需要补上这个父级菜单
                RoleMenu roleMenu=new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);//插入到数据库
                menuIdsCopy.add(menu.getPid());//补充到前端传过来的菜单id数组中去
            }
            //其他情况直接插入到数组
            RoleMenu roleMenu=new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    //根据角色id查询出所有绑定的菜单
    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
