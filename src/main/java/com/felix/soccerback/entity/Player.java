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
 * @since 2022-02-28
 */
@Getter
@Setter
  @TableName("club_player")
@ApiModel(value = "Player对象", description = "")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("球员号码")
        private Integer number;

      @ApiModelProperty("球员姓名")
      private String name;

      @ApiModelProperty("球员身高")
      private Integer weight;

      @ApiModelProperty("球员体重")
      private Integer height;

      @ApiModelProperty("球员年龄")
      private String age;

      @ApiModelProperty("球员位置")
      private String area;

      @ApiModelProperty("球员照片")
      private String image;


}