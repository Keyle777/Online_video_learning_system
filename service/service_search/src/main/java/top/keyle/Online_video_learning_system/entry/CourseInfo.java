package top.keyle.Online_video_learning_system.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CourseInfo {
    private String id;
    /**
     * 课程讲师ID
     */
    private String teacherId;
    /**
     * 课程专业ID
     */
    private String subjectId;
    /**
     * 课程的父id
     */
    private String subjectParentId;
    /**
     * 课程标题
     */
    private String title;
    /**
     * 课程销售价格，设置为0则可免费观看
     */
    private BigDecimal price;
    /**
     * 总课时
     */
    private Integer lessonNum;
    /**
     * 课程封面图片路径
     */
    private String cover;
    /**
     * 销售数量
     */
    private Long buyCount;
    /**
     * 浏览数量
     */
    private Long viewCount;
    /**
     * 乐观锁
     */
    private Long version;
    /**
     * 课程状态 Draft未发布  Normal已发布
     */
    private String status;
    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    private Integer isDeleted;
    /**
     * 创建时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ", timezone = "GMT+8")
    private Date gmtCreate;
    /**
     * 更新时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 课程简介
     */
    private String description;

    private String teacherName;
}
