package top.keyle.online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import top.keyle.online_video_learning_system.entry.EduVideo;
import top.keyle.online_video_learning_system.entry.vo.video.VideoInfoForm;

import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_video(课程视频表)】的数据库操作Service
* @createDate 2023-01-23 21:11:51
*/
public interface EduVideoService extends IService<EduVideo> {

    Integer getTheMaximumSort(@Param("chapterId") String chapterId);


    /**
     * 根据 chapter_id(章节ID)，查询是否存在此章节，存在则返回true
     * @param chapterId 章节ID
     * @return 存在则返回true，否则返回false
     */
    Boolean getCountByChapterId(String chapterId);

    /**
     * 保存video表单数据
     * @param videoInfoForm video表单数据
     * @return 保存成功返回true，失败返回false
     */
    Boolean saveVideoInfo(VideoInfoForm videoInfoForm);

    /**
     * 根据videoID查询视频，存在则返回video表单对象，失败抛出错误
     * @param id 视频ID
     * @return 存在则返回video表单对象，失败抛出错误
     */
    VideoInfoForm getVideoInfoFormById(String id);

    /**
     * 更新video
     * @param videoInfoForm 视频表单对象
     * @return 更新成功返回true，事失败返回false
     */
    Boolean updateVideoInfoById(VideoInfoForm videoInfoForm);
    // 根据视频资源ID删除
    Boolean deleteByVideoSourceId(String videoSourceId);

    /**
     * 根据videoID查询video获取它的视频资源ID，判断视频源ID是否为空，不为空则调用vod模块中的删除阿里云视频方法，删除视频资源。
     * @param id 视频ID
     * @return 删除成功返回true，失败返回false,远程资源删除失败则直接抛出异常。
     */
    Boolean removeVideoById(String id);

    /**
     * 根据视频IDs，先调用vod模块中的批量删除阿里云视频方法，然后再批量删除数据库中video记录
     * @param videoIdList 视频IDs
     * @return 删除成功返回true，失败返回false,远程资源删除失败则直接抛出异常。
     */
    Boolean removeVideoList(List<String> videoIdList);

    /**
     * 根据课程id，查询其下所有的视频，然后先删除其下所有的阿里云视频，再数据库中video记录
     * @param courseId 课程ID
     * @return 删除成功返回true，失败返回false,远程资源删除失败则直接抛出异常。
     */
    Boolean removeVideoByCourseId(String courseId);
}
