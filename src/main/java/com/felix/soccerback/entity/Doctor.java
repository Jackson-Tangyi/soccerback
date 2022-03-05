package com.felix.soccerback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-03-01
 */
@Getter
@Setter
  @TableName("club_doctor")
@ApiModel(value = "Doctor对象", description = "")
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("主键")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("名字")
      private String name;

      @ApiModelProperty("年龄")
      private Integer age;

      @ApiModelProperty("职务")
      private String career;

      @ApiModelProperty("性别")
      private String sex;

      @ApiModelProperty("描述")
      private String description;

      private String email;

      private String address;

      private String phone;

      @TableField(exist = false)
      private List<Cure> treatments;
}
