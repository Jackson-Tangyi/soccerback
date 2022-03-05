package com.felix.soccerback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Player;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Felix
 * @since 2022-02-28
 */
public interface IPlayerService extends IService<Player> {

    Page<Player> findPage(Page<Player> page, String name);
}
