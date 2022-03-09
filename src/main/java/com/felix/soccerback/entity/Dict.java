package com.felix.soccerback.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("club_dict")
@Data
public class Dict {

    private String name;
    private String value;
    private String type;

}
