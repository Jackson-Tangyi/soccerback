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
 * @since 2022-03-26
 */
@Getter
@Setter
  @TableName("club_achievement")
@ApiModel(value = "Achievement对象", description = "")
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("名称")
      private String name;

      @ApiModelProperty("赛季")
      private String season;

      @ApiModelProperty("奖杯图片url")
      private String image;


}
