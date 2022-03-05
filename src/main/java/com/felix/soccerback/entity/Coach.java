package com.felix.soccerback.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Felix
 * @since 2022-03-03
 */
@Getter
@Setter
  @TableName("club_coach")
@ApiModel(value = "Coach对象", description = "")
public class Coach implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("教练号码")
        private Integer id;

      @ApiModelProperty("教练姓名")
      private String name;

      @ApiModelProperty("教练生日")
      private String age;

      @ApiModelProperty("教练职务")
      private String career;

      @ApiModelProperty("性别")
      private String sex;

      @ApiModelProperty("描述")
      private String description;

      private String email;

      private String phone;

      private String address;

      private int cid;//外键

      @TableField(exist = false)
      private List<Game> games;

}
