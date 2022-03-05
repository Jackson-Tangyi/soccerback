package com.felix.soccerback.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.felix.soccerback.entity.Player;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Felix
 * @since 2022-02-28
 */
public interface PlayerMapper extends BaseMapper<Player> {

    Page<Player> findPage(Page<Player> page, @Param("name")String name);
}
