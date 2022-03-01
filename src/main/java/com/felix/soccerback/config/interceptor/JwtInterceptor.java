package com.felix.soccerback.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.felix.soccerback.common.Constants;
import com.felix.soccerback.entity.User;
import com.felix.soccerback.exception.ServiceException;
import com.felix.soccerback.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {  //自定义拦截器

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //执行认证
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"No token!Please login again! ");
        }
        //获取token中的user id
        String userId;
        try {
            userId= JWT.decode(token).getAudience().get(0);//在genToken中利用userId设置了载荷，现在解开
        }catch (JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401,"Verification failed！Please login again!");
        }
        //根据token中的userid查询数据库
        User user =userService.getById(userId);
        if(user == null){
            throw new ServiceException(Constants.CODE_401,"User not exists!Please login again!");
        }

        //  通过用户密码加签 验证token ，因为我生成token使用的也是Algorithm.HMAC256（）这个算法，所以这里我需要构造这个解析器
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try{
            jwtVerifier.verify(token);//验证token
        }catch (JWTVerificationException e){
            throw new ServiceException(Constants.CODE_401,"Verification failed！Please login again!");
        }

        return true;
    }
}
