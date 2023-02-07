package top.keyle.Online_video_learning_system.entry.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TMJIE5200
 * @date 2023-02-07 22:30:07
 * @week 星期二
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "条件查询轮播图对象")
public class CrmBannerVo {
    private String title;
    @ApiModelProperty(value = "查询开始时间",example = "2020-08-02 16:54:17")
    private String begin;
    @ApiModelProperty(value = "查询结束时间",example = "2022-11-15 16:54:17")
    private String end;
}
