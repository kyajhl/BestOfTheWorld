package com.fan.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
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
public class MessageAndName implements Serializable{

    @ApiModelProperty(value = "发布时间")
    private LocalDate messageDate;

    @ApiModelProperty(value = "留言内容")
    private String content;

    @ApiModelProperty(value = "手机号")
    private String mobilephone;

    @ApiModelProperty(value = "学生姓名")
    private String StudentName;

    public void setMessageInf(Message message) {
        this.setMessageDate(message.getMessageDate());
        this.setContent(message.getContent());
        this.setMobilephone(message.getMobilephone());
    }

}
