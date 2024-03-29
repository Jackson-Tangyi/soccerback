package com.felix.soccerback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
  @TableName("club_cure")
@ApiModel(value = "Cure对象", description = "")
public class Cure implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("球员id")
      private Integer pnum;

      @ApiModelProperty("队医id")
      private Integer did;

      @ApiModelProperty("治疗起始日期")
      @JsonFormat(pattern="yyyy-MM-dd")
      @DateTimeFormat(pattern = "yyyy-MM-dd")
      private Date start;

      @ApiModelProperty("治疗持续日期")
      private String end;

      @ApiModelProperty("描述状态能够上场")
      private String description;

      @ApiModelProperty("伤病部位")
      private String place;

      @TableField(exist = false)
      private String player;

      @TableField(exist = false)
      private String doctor;



}
