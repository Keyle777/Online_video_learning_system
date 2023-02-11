package top.keyle.online_video_learning_system.entity.vo.collectionVO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author TMJIE5200
 * @date 2023-02-11 20:31:04
 * @week 星期六
 */
@ApiModel(value = "收藏详情类",description = "收藏详情类")
@Data
public class CollectDetail {
    private String collectId;
    private String courseId;
    private String teacherName;
    private String subjectTitle;
    private String courseTitle;
    private BigDecimal coursePrice;
    private String courseDescription;
    private Date collectGmtCreate;

}
