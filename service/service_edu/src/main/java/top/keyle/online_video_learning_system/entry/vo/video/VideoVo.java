package top.keyle.online_video_learning_system.entry.vo.video;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "课时信息",description = "小节封装类")
@Data
public class VideoVo {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "视频ID")
    private String id;
    @ApiModelProperty(value = "节点名称")
    private String title;
    @ApiModelProperty(value = "视频资源")
    private String videoSourceId;
    @ApiModelProperty(value = "是否可以试听：0默认 1免费")
    private Integer free;

}
