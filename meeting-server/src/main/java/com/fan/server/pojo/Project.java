package com.fan.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
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
@TableName("project")
@ApiModel(value="Project对象", description="")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "项目id")
    @TableField("project_id")
    private Integer projectId;

    @ApiModelProperty(value = "项目名字")
    @TableField("project_name")
    private String projectName;

    @ApiModelProperty(value = "开始日期")
    @TableField("begin_date")
    private LocalDate beginDate;

    @ApiModelProperty(value = "结束日期")
    @TableField("end_date")
    private LocalDate endDate;

    @ApiModelProperty(value = "项目简介")
    private String content;

    @ApiModelProperty(value = "最大容量")
    private Integer volume;

    @ApiModelProperty(value = "验收标准")
    @TableField("acceptance_criteria")
    private String acceptanceCriteria;


}
