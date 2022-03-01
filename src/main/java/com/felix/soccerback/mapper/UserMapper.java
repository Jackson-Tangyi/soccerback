package com.felix.soccerback.mapper;

import com.felix.soccerback.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.felix.soccerback.controller.dto.UserPasswordDTO;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Felix
 * @since 2022-02-26
 */
public interface UserMapper extends BaseMapper<User> {

    @Update("update club_user set password = #{newPassword} where username = #{username} and password = #{password}")
    int updatePassword(UserPasswordDTO userPasswordDTO);

}
