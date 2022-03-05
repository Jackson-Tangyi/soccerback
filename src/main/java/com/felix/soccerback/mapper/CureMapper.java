package com.felix.soccerback.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Cure;
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
public interface CureMapper extends BaseMapper<Cure> {

    Page<Cure> findPage(Page<Cure> page, @Param("description") String description);
}
