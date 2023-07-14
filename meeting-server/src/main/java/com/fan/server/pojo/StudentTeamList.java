package com.fan.server.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

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
public class StudentTeamList implements Serializable{

    @ApiModelProperty(value = "项目名")
    private String projectName;

    @ApiModelProperty(value = "团队名")
    private String teamName;

    @ApiModelProperty(value = "团队人数")
    private Integer teamNumber;

}
