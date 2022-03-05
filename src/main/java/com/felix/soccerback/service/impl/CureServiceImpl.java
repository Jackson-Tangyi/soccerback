package com.felix.soccerback.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Cure;
import com.felix.soccerback.mapper.CureMapper;
import com.felix.soccerback.service.ICureService;
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
public class CureServiceImpl extends ServiceImpl<CureMapper, Cure> implements ICureService {

    @Resource
    private CureMapper cureMapper;

    @Override
    public Page<Cure> findPage(Page<Cure> page, String description) {
        return cureMapper.findPage(page,description);
    }
}
