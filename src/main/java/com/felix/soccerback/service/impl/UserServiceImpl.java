package com.felix.soccerback.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.felix.soccerback.common.Constants;
import com.felix.soccerback.entity.User;
import com.felix.soccerback.controller.dto.UserDTO;
import com.felix.soccerback.controller.dto.UserPasswordDTO;
import com.felix.soccerback.exception.ServiceException;
import com.felix.soccerback.mapper.UserMapper;
import com.felix.soccerback.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.felix.soccerback.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Felix
 * @since 2022-02-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG=Log.get();
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one =getUserInfo(userDTO);
        if(one!=null){
            BeanUtil.copyProperties(one,userDTO,true);//把user查到的信息返回给userDTO
            //设置token-->首先利用user对象将userId和密码传给工具类生成Token，然后再把生成的Token传给userDTO
            userDTO.setToken(TokenUtils.genToken(one.getId().toString(),one.getPassword()));
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"Username or Password Error!");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        User one =getUserInfo(userDTO);
        if(one==null){
            one =new User();//我要插入新的User，不是已经有的
            BeanUtil.copyProperties(userDTO,one,true);//把源Bean对象（userDTO）的属性拷贝给目标Bean对象(User)
            save(one);
        }else {
            throw new ServiceException(Constants.CODE_600,"User already exists!");
        }
        return one;
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600, "密码错误");
        }
    }

    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername()).eq("password",userDTO.getPassword());
        User one;
        try{
            one=getOne(queryWrapper);//从数据库查询用户信息
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"System Error");
        }
        return one;
    }
}
