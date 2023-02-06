package top.keyle.online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
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
     * 按courseId(课程ID) 获取章节以及对应的video信息
     * @param courseId 课程ID
     * @return ChapterVo(章节信息)
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 根据chapter_id(章节ID)删除章节以及其下的课时
     * @param id 章节ID
     * @return 没有video记录则删除章节成功true/失败false，否抛出错误。
     */
    Boolean removeChapterById(String id);

    /**
     * 根据课程id删除其下所有章节和章节下面的所有video
     * @param courseId 课程id
     * @return 成功返回true，失败返回false
     */
    Boolean removeChapterByCourseId(String courseId);

    /**
     * 根据课程ID查询其下章节中sort最大的数
     * @return 最大的sort
     */
    Integer selectMaxSortByCourseId(@Param("courseId") String courseId);
}
