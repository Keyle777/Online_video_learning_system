package top.keyle.online_video_learning_system.entry.vo.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import top.keyle.online_video_learning_system.entry.vo.video.VideoVo;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "章节封装类",description = "章节封装类")
@Data
public class ChapterVo {

    private String id;

    private String title;

    //表示小节
    private List<VideoVo> children = new ArrayList<>();
}
