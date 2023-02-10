package top.keyle.online_video_learning_system.entry.vo.eduCourse;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author TMJIE5200
 * @date 2023-02-10 13:19:36
 * @week 星期五
 */
@Data
public class CourseAndTeacherVO {
    private static final long serialVersionUID = 1L;
    private String CourseId;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String teacherName;
    private BigDecimal price;
    private Long buyCount;
    private Long viewCount;
    @JSONField(format="yyyy年MM月dd日")
    private Date gmtCreate;
    private String teacherId;
}
