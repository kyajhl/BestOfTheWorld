package com.fan.server.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

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
public class StudentAllInf {

    @ApiModelProperty(value = "学号")
    private String studentId;

    @ApiModelProperty(value = "姓名")
    private String studentName;

    @ApiModelProperty(value = "电话号码")
    private String mobilephone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

    @ApiModelProperty(value = "学校")
    private String college;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "班级")
    private String majorClass;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "团队id")
    private String teamId;

    @ApiModelProperty(value = "团队人数")
    private String teamNumber;

    @ApiModelProperty(value = "团队名字")
    private String teamName;

    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "成绩")
    private String grade;

}
