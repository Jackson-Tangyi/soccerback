package com.felix.soccerback.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
  @TableName("club_game")
@ApiModel(value = "Game对象", description = "")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("比赛场次号")
        private Integer id;

      @ApiModelProperty("比赛场地名称")
      private String name;

      @ApiModelProperty("比赛日期")
      @DateTimeFormat(pattern="yyyy-MM-dd")//页面写入数据库时格式化
      @JsonFormat(pattern="yyyy-MM-dd")
      private Date date;

      @ApiModelProperty("主客场")
      private String homeaway;

      @ApiModelProperty("最终比分（统计球员的进球数）")
      private String score;

      private Integer coachid;

      private String place;

      @ApiModelProperty("比赛类型")
      private String type;

      @TableField(exist = false)
      private String coach;


}
