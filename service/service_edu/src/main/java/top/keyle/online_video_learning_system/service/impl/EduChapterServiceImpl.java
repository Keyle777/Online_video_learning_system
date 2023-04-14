package top.keyle.online_video_learning_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.online_video_learning_system.entry.EduChapter;
import top.keyle.online_video_learning_system.entry.EduVideo;
import top.keyle.online_video_learning_system.entry.vo.chapter.ChapterVo;
import top.keyle.online_video_learning_system.entry.vo.video.VideoVo;
import top.keyle.online_video_learning_system.mapper.EduChapterMapper;
import top.keyle.online_video_learning_system.mapper.EduVideoMapper;
import top.keyle.online_video_learning_system.service.EduChapterService;
import top.keyle.online_video_learning_system.service.EduVideoService;

import java.util.ArrayList;
import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_chapter(章节表)】的数据库操作Service实现
* @createDate 2023-01-23 21:04:20
*/
@Service
@SuppressWarnings("all")
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;

    @Autowired
    EduVideoMapper eduVideoMapper;
    @Autowired
    EduChapterMapper eduChapterMapper;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //最终要的到的数据列表
        ArrayList<ChapterVo> chapterVoArrayList = new ArrayList<>();
        //获取章节信息
        QueryWrapper<EduChapter> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id", courseId);
        queryWrapper1.orderByAsc("sort", "id");
        List<EduChapter> chapters = baseMapper.selectList(queryWrapper1);
        //获取课时信息
        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", courseId);
        queryWrapper2.orderByAsc("sort", "id");
        List<EduVideo> videos = eduVideoService.list(queryWrapper2);
        //填充章节vo数据
        int count1 = chapters.size();
        for (int i = 0; i < count1; i++) {
            EduChapter chapter = chapters.get(i);
            //创建章节vo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoArrayList.add(chapterVo);
            //填充课时vo数据
            ArrayList<VideoVo> videoVoArrayList = new ArrayList<>();
            int count2 = videos.size();
            for (int j = 0; j < count2; j++) {
                EduVideo video = videos.get(j);
                if(chapter.getId().equals(video.getChapterId())){
                    //创建课时vo对象
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoArrayList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoArrayList);
        }
        return chapterVoArrayList;
    }


    @Override
    public Boolean removeChapterById(String id) {
        // 1、判断章节的ID下面是否存在小节
        List<EduVideo> eduVideos = eduVideoMapper.selectByChapterId(id);
        // 2、执行到这里说明此章节id下没有查到video记录,可以删除
        if(eduVideos.size() != 0){
            for (EduVideo eduVideo : eduVideos) {
                QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
                String chapterId = eduVideo.getChapterId();
                String videoSourceId = eduVideo.getId();
                eduVideoService.removeVideoById(videoSourceId);
                videoQueryWrapper.eq("chapter_id",chapterId);
                eduVideoService.remove(videoQueryWrapper);
            }
        }
        int delete = eduChapterMapper.deleteById(id);
        return delete > 0;
    }

    @Override
    public Boolean removeChapterByCourseId(String courseId) {
        // 判断章节的ID下面是否存在小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduVideo> list = eduVideoService.list(wrapper);
        if(list.size() != 0){
            eduVideoService.removeVideoByCourseId(courseId);
        }
        // 2、如果有不能删除直接false
        int i = eduChapterMapper.deleteByCourseId(courseId);
        // 3、删除章节
        return i == 1;
    }

    @Override
    public Integer selectMaxSortByCourseId(@Param("courseId") String courseId) {
        return eduChapterMapper.selectMaxSortByCourseId(courseId);
    }
}





