package com.felix.soccerback.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Doctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Felix
 * @since 2022-03-01
 */
public interface DoctorMapper extends BaseMapper<Doctor> {

    Page<Doctor> findPage(Page<Doctor> page, @Param("name") String name,@Param("email") String email,@Param("address") String address);
}
