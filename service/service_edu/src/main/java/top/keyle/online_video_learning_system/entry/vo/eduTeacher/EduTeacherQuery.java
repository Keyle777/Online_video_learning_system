package top.keyle.online_video_learning_system.entry.vo.eduTeacher;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TMJIE5200
 * @date 2022-11-10 20:05:02
 * @week 星期四
 * todo 讲师查询条件对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "条件查询讲师对象")
public class EduTeacherQuery {
    @ApiModelProperty(value = "讲师名称，模糊查询")
    private String name;
    /**
     * todo String是因为前端传给后端的时间直接就是字符串类型，省得去转换了
     */
    @ApiModelProperty(value = "查询开始时间",example = "2020-08-02 16:54:17")
    private String begin;
    @ApiModelProperty(value = "查询结束时间",example = "2022-11-15 16:54:17")
    private String end;
    @ApiModelProperty(value = "头衔:1高级讲师，2首席讲师",example = "1")
    private Integer level;
}
