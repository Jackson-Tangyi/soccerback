package com.felix.soccerback.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Coach;
import com.felix.soccerback.mapper.CoachMapper;
import com.felix.soccerback.service.ICoachService;
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
public class CoachServiceImpl extends ServiceImpl<CoachMapper, Coach> implements ICoachService {

    @Resource
    private CoachMapper coachMapper;

    @Override
    public Page<Coach> findPage(Page<Coach> page, String name, String email, String address) {
        return coachMapper.findPage(page,name,email,address);
    }
}
