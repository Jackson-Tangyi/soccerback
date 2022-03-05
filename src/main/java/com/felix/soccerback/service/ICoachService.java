package com.felix.soccerback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Coach;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Felix
 * @since 2022-03-03
 */
public interface ICoachService extends IService<Coach> {

    Page<Coach> findPage(Page<Coach> objectPage, String name, String email, String address);
}
