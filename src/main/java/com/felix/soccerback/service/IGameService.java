package com.felix.soccerback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Game;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Felix
 * @since 2022-03-03
 */
public interface IGameService extends IService<Game> {

    Page<Game> findPage(Page<Game> page, String name);
}
