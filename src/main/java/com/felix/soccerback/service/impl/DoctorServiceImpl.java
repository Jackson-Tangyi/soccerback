package com.felix.soccerback.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Doctor;
import com.felix.soccerback.mapper.DoctorMapper;
import com.felix.soccerback.service.IDoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Felix
 * @since 2022-03-01
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements IDoctorService {

    @Resource
    private DoctorMapper doctorMapper;

    @Override
    public Page<Doctor> findPage(Page<Doctor> page, String name, String email, String address) {
        return doctorMapper.findPage(page,name,email,address);
    }
}
