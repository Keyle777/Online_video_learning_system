package top.keyle.online_video_learning_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.keyle.online_video_learning_system.entry.EduVideo;
import top.keyle.online_video_learning_system.entry.vo.video.VideoInfoForm;
import top.keyle.online_video_learning_system.mapper.EduVideoMapper;
import top.keyle.online_video_learning_system.service.EduVideoService;
import top.keyle.universal_tool.GlobalException;
import top.keyle.universal_tool.RespBeanEnum;

/**
 * @author TMJIE5200
 * @description 针对表【edu_video(课程视频表)】的数据库操作Service实现
 * @createDate 2023-01-23 21:11:51
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo>
        implements EduVideoService {
    @Override
    public boolean getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        Long count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public Boolean saveVideoInfo(VideoInfoForm videoInfoForm) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm, video);
        boolean result = this.save(video);
        if (!result) {
            throw new GlobalException(RespBeanEnum.LESSON_SAVING_FAILED);
        }
        return true;
    }

    @Override
    public VideoInfoForm getVideoInfoFormById(String id) {
        //从video表中取数据
        EduVideo video = this.getById(id);
        if (video == null) {
            throw new GlobalException(RespBeanEnum.THE_DATA_DOES_NOT_EXIST);
        }
        //创建videoInfoForm对象
        VideoInfoForm videoInfoForm = new VideoInfoForm();
        BeanUtils.copyProperties(video, videoInfoForm);
        return videoInfoForm;
    }

    @Override
    public Boolean updateVideoInfoById(VideoInfoForm videoInfoForm) {
        //保存课时基本信息
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm, video);
        boolean result = this.updateById(video);
        if (!result) {
            throw new GlobalException(RespBeanEnum.LESSON_SAVING_FAILED);
        }
        return true;
    }

    @Override
    public boolean removeVideoById(String id) {
        Integer result = baseMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        Integer count = baseMapper.delete(queryWrapper);
        return count > 0;
    }
}




