package top.keyle.online_video_learning_system.entry.vo.video;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "小节封装类",description = "小节封装类")
@Data
public class VideoVo {

    private String id;

    private String title;

}
