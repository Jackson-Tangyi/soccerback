package com.felix.soccerback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2022-03-25
 */
@Getter
@Setter
  @TableName("club_history")
@ApiModel(value = "History对象", description = "")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("内容")
      private String content;

      @ApiModelProperty("创建时间")
      @DateTimeFormat(pattern="yyyy-MM-dd")//页面写入数据库时格式化
      @JsonFormat(pattern="yyyy-MM-dd")
      private Date createTime;

      @ApiModelProperty("图标")
      private String icon;

      @ApiModelProperty("颜色")
      private String color;

      @ApiModelProperty("照片地址")
      private String url;


}
