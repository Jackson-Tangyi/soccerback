package com.felix.soccerback.service;

import com.felix.soccerback.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Felix
 * @since 2022-03-08
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
