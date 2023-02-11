package top.keyle.online_video_learning_system.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author TMJIE5200
 * @date 2023-02-11 18:05:51
 * @week 星期六
 */
@Data
@TableName(value ="edu_course_collect")
@AllArgsConstructor
@NoArgsConstructor
public class EduCourseCollect implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    @TableId
    private String id;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "课程专业ID")
    private String memberId;

    @TableLogic(value = "0", delval = "1")
    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
