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
 * @since 2023-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("team")
@ApiModel(value="Team对象", description="")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "团队名字")
    @TableField("team_name")
    private String teamName;

    @ApiModelProperty(value = "团队人数")
    @TableField("team_number")
    private Integer teamNumber;

    @ApiModelProperty(value = "项目id")
    @TableField("project_id")
    private Integer projectId;

    @ApiModelProperty(value = "团队id")
    @TableField("team_id")
    private String teamId;


}
