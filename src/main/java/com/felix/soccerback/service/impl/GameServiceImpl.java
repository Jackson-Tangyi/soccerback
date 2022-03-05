package com.felix.soccerback.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Game;
import com.felix.soccerback.mapper.GameMapper;
import com.felix.soccerback.service.IGameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Felix
 * @since 2022-03-03
 */
@Service
public class GameServiceImpl extends ServiceImpl<GameMapper, Game> implements IGameService {

    @Resource
    private GameMapper gameMapper;

    @Override
    public Page<Game> findPage(Page<Game> page, String name) {
        return gameMapper.findPage(page,name);
    }
}
