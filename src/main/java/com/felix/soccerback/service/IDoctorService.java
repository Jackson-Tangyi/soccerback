package com.felix.soccerback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Doctor;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Felix
 * @since 2022-03-01
 */
public interface IDoctorService extends IService<Doctor> {

    Page<Doctor> findPage(Page<Doctor> page, String name, String email, String address);
}
