package top.keyle.online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.keyle.online_video_learning_system.entry.EduChapter;
import top.keyle.online_video_learning_system.entry.vo.chapter.ChapterVo;

import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_chapter(章节表)】的数据库操作Service
* @createDate 2023-01-23 21:04:20
*/
public interface EduChapterService extends IService<EduChapter> {
    /**
     * 按课程 ID 获取章节以及对应的小节视频
     * @param courseId
     * @return
     */
    public List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
