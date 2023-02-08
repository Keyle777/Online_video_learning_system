package top.keyle.online_video_learning_system.entry.vo.eduCourse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;
    @ApiModelProperty(value = "二级类别id")
    private String subjectId;
    @ApiModelProperty(value = "销量")
    private Long buyCountSort;
    @ApiModelProperty(value = "创建日期")
    private String gmtCreateSort;
    @ApiModelProperty(value = "价格")
    private BigDecimal priceSort;
}
