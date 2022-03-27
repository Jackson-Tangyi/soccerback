package com.felix.soccerback.mapper;

import com.felix.soccerback.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Felix
 * @since 2022-03-08
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from club_role where flag=#{flag}")
    Integer selectByFlag(@Param("flag") String flag);

}
