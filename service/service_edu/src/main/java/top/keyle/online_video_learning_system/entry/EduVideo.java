package top.keyle.online_video_learning_system.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程视频表
 * @TableName edu_video
 */
@TableName(value ="edu_video")
@Data
public class EduVideo implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 视频ID
     */
    @ApiModelProperty(value = "视频ID")
    @TableId
    private String id;

    @ApiModelProperty(value = "视频封面")
    private String cover;
    /**
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID")

    private String courseId;
    /**
     * 章节ID
     */
    @ApiModelProperty(value = "章节ID")
    private String chapterId;
    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    private String title;

    /**
     * 云端视频资源
     */
    @ApiModelProperty(value = "视频资源")
    private String videoSourceId;
    /**
     * 原始文件名称
     */
    @ApiModelProperty(value = "云服务器上存储的视频文件名称")
    private String videoOriginalName;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private Integer sort;
    /**
     * 视频播放总次数
     */
    @ApiModelProperty(value = "播放次数")
    private Long playCount;
    /**
     * 是否可以试听：0收费 1免费
     */
    @ApiModelProperty(value = "是否可以试听：0免费 1收费")
    private Integer isFree;
    /**
     * 视频时长（秒）
     */
    @ApiModelProperty(value = "视频时长（秒）")
    private Double duration;
    /**
     * Empty未上传 Transcoding转码中  Normal正常
     */
    @ApiModelProperty(value = "视频状态:见阿里云文档")
    private String status;
    /**
     * 视频源文件大小（字节）
     */
    @ApiModelProperty(value = "视频源文件大小（字节）")
    private Long size;
    /**
     * 乐观锁
     */

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "创建时间", example = "2019-01-01 8:00:00")
    @TableField(fill = FieldFill.INSERT)    /**
     * 创建时间
     */
    private Date gmtCreate;
    @ApiModelProperty(value = "更新时间", example = "2019-01-01 8:00:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    /**
     * 更新时间
     */
    private Date gmtModified;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        EduVideo other = (EduVideo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
            && (this.getChapterId() == null ? other.getChapterId() == null : this.getChapterId().equals(other.getChapterId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getVideoSourceId() == null ? other.getVideoSourceId() == null : this.getVideoSourceId().equals(other.getVideoSourceId()))
            && (this.getVideoOriginalName() == null ? other.getVideoOriginalName() == null : this.getVideoOriginalName().equals(other.getVideoOriginalName()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getPlayCount() == null ? other.getPlayCount() == null : this.getPlayCount().equals(other.getPlayCount()))
            && (this.getIsFree() == null ? other.getIsFree() == null : this.getIsFree().equals(other.getIsFree()))
            && (this.getDuration() == null ? other.getDuration() == null : this.getDuration().equals(other.getDuration()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSize() == null ? other.getSize() == null : this.getSize().equals(other.getSize()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCourseId() == null) ? 0 : getCourseId().hashCode());
        result = prime * result + ((getChapterId() == null) ? 0 : getChapterId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getVideoSourceId() == null) ? 0 : getVideoSourceId().hashCode());
        result = prime * result + ((getVideoOriginalName() == null) ? 0 : getVideoOriginalName().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getPlayCount() == null) ? 0 : getPlayCount().hashCode());
        result = prime * result + ((getIsFree() == null) ? 0 : getIsFree().hashCode());
        result = prime * result + ((getDuration() == null) ? 0 : getDuration().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSize() == null) ? 0 : getSize().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", courseId=").append(courseId);
        sb.append(", chapterId=").append(chapterId);
        sb.append(", title=").append(title);
        sb.append(", videoSourceId=").append(videoSourceId);
        sb.append(", videoOriginalName=").append(videoOriginalName);
        sb.append(", sort=").append(sort);
        sb.append(", playCount=").append(playCount);
        sb.append(", isFree=").append(isFree);
        sb.append(", duration=").append(duration);
        sb.append(", status=").append(status);
        sb.append(", size=").append(size);
        sb.append(", version=").append(version);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
