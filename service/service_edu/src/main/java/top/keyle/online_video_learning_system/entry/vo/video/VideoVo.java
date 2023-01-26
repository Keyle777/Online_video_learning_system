package top.keyle.online_video_learning_system.entry.vo.video;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "课时信息",description = "小节封装类")
@Data
public class VideoVo {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    // 0收费 1免费
    private Integer free;

}
