package com.felix.soccerback.service.impl;

import com.felix.soccerback.entity.Game;
import com.felix.soccerback.mapper.GameMapper;
import com.felix.soccerback.service.IGameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
