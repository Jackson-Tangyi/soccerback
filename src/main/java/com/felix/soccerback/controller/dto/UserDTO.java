package com.felix.soccerback.controller.dto;

import lombok.Data;

/*
 *接受前端登录请求参数，让实体类User专注于跟数据业务打交道
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private String avatarUrl;
    private String token;
}
