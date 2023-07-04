package com.fan.server.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author BestOfTheWorld
 * @since 2023-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment")
@ApiModel(value="Comment对象", description="")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "团队id，外键")
    @TableField("team_id")
    private Integer teamId;

    @ApiModelProperty(value = "教师id，外键")
    @TableField("teacher_id")
    private Integer teacherId;

    @ApiModelProperty(value = "评价")
    private String content;

    @ApiModelProperty(value = "成绩")
    private Integer grade;


}
