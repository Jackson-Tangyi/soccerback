package com.felix.soccerback.service.impl;

import com.felix.soccerback.entity.Player;
import com.felix.soccerback.mapper.PlayerMapper;
import com.felix.soccerback.service.IPlayerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
