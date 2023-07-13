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
public class StudentTeamInf implements Serializable {

    @ApiModelProperty(value = "学生id")
    private Integer studentId;

    @ApiModelProperty(value = "团队id")
    private String teamId;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "成绩")
    private String grade;

    @ApiModelProperty(value = "手机号")
    private String mobilephone;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    public void setStudentTeamInf(StudentTeam studentTeam) {
        this.setStudentId(studentTeam.getStudentId());
        this.setTeamId(studentTeam.getTeamId());
        this.setGrade(studentTeam.getGrade());
        this.setPosition(studentTeam.getPosition());
    }

}
