package com.felix.soccerback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Cure;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Felix
 * @since 2022-03-03
 */
public interface ICureService extends IService<Cure> {

    Page<Cure> findPage(Page<Cure> page, String description);
}
