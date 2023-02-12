package top.keyle.Online_video_learning_system.entry;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 评论
 *
 * @author TMJIE5200
 * @TableName edu_comment
 */
@TableName(value = "edu_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduComment implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String courseId;

    private String teacherId;

    private String memberId;

    private String nickname;
    private String avatar;

    private String star;

    private String content;
    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @TableLogic(value = "0", delval = "1")
    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;
    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    /**
     * 更新时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EduComment that = (EduComment) o;
        return Objects.equals(id, that.id) && Objects.equals(courseId, that.courseId) && Objects.equals(teacherId, that.teacherId) && Objects.equals(memberId, that.memberId) && Objects.equals(nickname, that.nickname) && Objects.equals(avatar, that.avatar) && Objects.equals(star, that.star) && Objects.equals(content, that.content) && Objects.equals(isDeleted, that.isDeleted) && Objects.equals(gmtCreate, that.gmtCreate) && Objects.equals(gmtModified, that.gmtModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, teacherId, memberId, nickname, avatar, star, content, isDeleted, gmtCreate, gmtModified);
    }

    @Override
    public String toString() {
        return "EduComment{" +
                "id='" + id + '\'' +
                ", courseId='" + courseId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", star='" + star + '\'' +
                ", content='" + content + '\'' +
                ", isDeleted=" + isDeleted +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
