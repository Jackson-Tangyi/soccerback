package com.felix.soccerback.service.impl;

import com.felix.soccerback.entity.Doctor;
import com.felix.soccerback.mapper.DoctorMapper;
import com.felix.soccerback.service.IDoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
