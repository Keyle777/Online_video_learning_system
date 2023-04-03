package top.keyle.online_video_learning_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.online_video_learning_system.client.VodClient;
import top.keyle.online_video_learning_system.entry.EduVideo;
import top.keyle.online_video_learning_system.entry.vo.video.VideoInfoForm;
import top.keyle.online_video_learning_system.mapper.EduVideoMapper;
import top.keyle.online_video_learning_system.service.EduVideoService;
import top.keyle.universal_tool.GlobalException;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TMJIE5200
 * @description 针对表【edu_video(课程视频表)】的数据库操作Service实现
 * @createDate 2023-01-23 21:11:51
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo>
        implements EduVideoService {

    @Autowired
    VodClient vodClient;

    @Autowired
    EduVideoMapper eduVideoMapper;
    @Override
    public Boolean getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        // 根据 chapter_id 条件，查询总记录数
        Long count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    /**
     根据传入的视频信息表单保存视频信息
     @param videoInfoForm 视频信息表单对象
     @return 保存结果，true为保存成功，false为保存失败
     @throws GlobalException 当保存失败时，抛出全局异常
     */
    @Override
    public Boolean saveVideoInfo(VideoInfoForm videoInfoForm) {
        // 创建EduVideo对象并将videoInfoForm中的属性复制到EduVideo对象中
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm, video);
        // 保存EduVideo对象
        boolean result = this.save(video);
        // 如果保存失败，则抛出全局异常
        if (!result) {
            throw new GlobalException(RespBeanEnum.LESSON_SAVING_FAILED);
        }
        // 保存成功，返回true
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
    public Integer getTheMaximumSort(@Param("chapterId") String chapterId) {
        return eduVideoMapper.getTheMaximumSort(chapterId);
    }

    @Override
    public Boolean deleteByVideoSourceId(String videoSourceId) {
        RespBean respBean = vodClient.deleteAliVideoByVideoSourceId(videoSourceId);
        if(respBean.getCode() == 20000){
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeVideoById(String id) {
        //1、查询云端视频id
        EduVideo eduVideo = baseMapper.selectById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //2、判断视频源ID是否为空，不为空则调用vod模块中的删除阿里云视频方法，删除视频资源。
        if (!StringUtils.isEmpty(videoSourceId)) {
            RespBean respBean = vodClient.deleteAliVideoByVideoSourceId(videoSourceId);
            if (respBean.getCode() == 20008) {
                throw new GlobalException(RespBeanEnum.DELETING_VIDEO_FAILED);
            }
        }
        //3、删除数据库中video记录
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public Boolean removeVideoList(List<String> videoIdList) {
        ArrayList<String> videoSourceIds = new ArrayList<>();
        //1、根据视频ID获取阿里云视频ID并加入到List集合中
        for (String videoID :
                videoIdList) {
            String videoSourceId = baseMapper.selectById(videoID).getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoSourceIds.add(videoSourceId);
            }
        }
        //2、调用vod模块中的批量删除阿里云视频方法，删除视频资源。
        if (videoSourceIds.size() > 0 ){
            RespBean respBean = vodClient.deleteBatchByVideoSourceIds(videoSourceIds);
            if (respBean.getCode() == 20008) {
                throw new GlobalException(RespBeanEnum.DELETING_VIDEO_FAILED);
            }
        }
        //3、批量删除数据库中video记录
        return baseMapper.deleteBatchIds(videoIdList) > 0;
    }

    @Override
    public Boolean removeVideoByCourseId(String courseId) {
        // 1. 根据课程id查询其下所有的video记录
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);
        System.out.println(eduVideoList);
        // 2、获取video中阿里云资源id
        List<String> videoIds = new ArrayList<>();
        for (EduVideo video : eduVideoList) {
            videoIds.add(video.getVideoSourceId());
        }
        // 3、根据多个视频id删除多个视频
        if(videoIds.size() > 0){
            RespBean respBean = vodClient.deleteBatchByVideoSourceIds(videoIds);
            if (respBean.getCode() == 20008) {
                throw new GlobalException(RespBeanEnum.DELETING_VIDEO_FAILED);
            }
        }
        // 4、根据课程ID删除课程记录
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        return baseMapper.delete(wrapper) > 0;
    }
}




