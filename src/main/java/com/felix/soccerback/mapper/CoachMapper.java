package com.felix.soccerback.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Coach;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Felix
 * @since 2022-03-03
 */
public interface CoachMapper extends BaseMapper<Coach> {

    Page<Coach> findPage(Page<Coach> page, @Param("name") String name, @Param("email") String email,@Param("address") String address);
}
