package com.fan.server.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
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
@TableName("team_log")
@ApiModel(value="TeamLog对象", description="")
public class TeamLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "日志内容")
    private String content;

    @ApiModelProperty(value = "团队id")
    @TableField("team_id")
    private Integer teamId;

    @ApiModelProperty(value = "发布时间")
    @TableField("log_date")
    private LocalDateTime logDate;


}
