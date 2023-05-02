package top.keyle.Online_video_learning_system.entry;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author TMJIE5200
 * @date 2023-02-08 18:08:16
 * @week 星期三
 */
@ApiModel(value = "前端课时信息", description = "用户端课程查询对象封装")
@Data
public class CourseFrontQuery {

    private static final long serialVersionUID = 1L;
    private String subjectParentId;
    private String subjectId;
    private Long buyCountSort;
    private String gmtCreateSort;
    private BigDecimal priceSort;
    private String searchText;
    private String teacherId;
}
