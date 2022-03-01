package com.felix.soccerback.service;

import com.felix.soccerback.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.felix.soccerback.controller.dto.UserDTO;
import com.felix.soccerback.controller.dto.UserPasswordDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Felix
 * @since 2022-02-26
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);
}
