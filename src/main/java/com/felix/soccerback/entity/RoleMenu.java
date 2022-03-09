package com.felix.soccerback.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//角色菜单绑定
@TableName("club_role_menu")
@Data
public class RoleMenu {

    private Integer roleId;
    private Integer menuId;

}
