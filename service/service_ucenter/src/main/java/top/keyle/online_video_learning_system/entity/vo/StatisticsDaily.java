package top.keyle.online_video_learning_system.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDaily{
    @ApiModelProperty(value = "学员注册人数")
    private Integer registerNum;

    @ApiModelProperty(value = "学员登录总数")
    private Integer loginNum;

    @ApiModelProperty(value = "视频浏览次数")
    private Integer videoViewNum;

    @ApiModelProperty(value = "新增课程数")
    private Integer courseNum;
}
