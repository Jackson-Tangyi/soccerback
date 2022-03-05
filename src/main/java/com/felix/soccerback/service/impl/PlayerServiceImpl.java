package com.felix.soccerback.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Player;
import com.felix.soccerback.mapper.PlayerMapper;
import com.felix.soccerback.service.IPlayerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Felix
 * @since 2022-02-28
 */
@Service
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Player> implements IPlayerService {

    @Resource
    private PlayerMapper playerMapper;

    @Override
    public Page<Player> findPage(Page<Player> page, String name) {
        return playerMapper.findPage(page,name);
    }
}
