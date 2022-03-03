package com.felix.soccerback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
  @TableName("club_director")
@ApiModel(value = "Director对象", description = "")
public class Director implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("名字")
      private String name;

    private Integer age;

    private String career;

    private String description;

      @ApiModelProperty("性别")
      private String sex;

    private String address;

    private String eamial;

    private String phone;


}
