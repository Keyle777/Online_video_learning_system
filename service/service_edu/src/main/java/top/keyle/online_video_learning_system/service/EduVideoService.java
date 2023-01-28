package top.keyle.online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.keyle.online_video_learning_system.entry.EduVideo;
import top.keyle.online_video_learning_system.entry.vo.video.VideoInfoForm;

/**
* @author TMJIE5200
* @description 针对表【edu_video(课程视频表)】的数据库操作Service
* @createDate 2023-01-23 21:11:51
*/
public interface EduVideoService extends IService<EduVideo> {

    boolean getCountByChapterId(String chapterId);
    // 保存video表单数据
    Boolean saveVideoInfo(VideoInfoForm videoInfoForm);
    // 根据ID查询课时
    VideoInfoForm getVideoInfoFormById(String id);
    // 更新课时
    Boolean updateVideoInfoById(VideoInfoForm videoInfoForm);
    // 根据课时ID删除课时
    boolean removeVideoById(String id);
}
