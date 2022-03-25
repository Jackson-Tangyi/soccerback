package com.felix.soccerback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author Felix
 * @since 2022-02-26
 */
@Getter
@Setter
@TableName("club_user")
@ApiModel(value = "User对象", description = "")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户名")
      private String username;

      @ApiModelProperty("密码")
      private String password;

      @ApiModelProperty("年龄")
      private Integer age;

      @ApiModelProperty("性别")
      private String sex;

      @ApiModelProperty("邮箱")
      private String email;

      @ApiModelProperty("地址")
      private String address;

      @ApiModelProperty("创建时间")
      private Date createTime;

      @ApiModelProperty("照片URL")
      private String avatarUrl;

      @ApiModelProperty("用户类型")
      private String role;


}
