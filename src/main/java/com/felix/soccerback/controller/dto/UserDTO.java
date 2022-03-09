package com.felix.soccerback.controller.dto;

import com.felix.soccerback.entity.Menu;
import lombok.Data;

import java.util.List;

/*
 *接受前端登录请求参数，让实体类User专注于跟数据业务打交道
 */
@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;//该角色对应的菜单
}
