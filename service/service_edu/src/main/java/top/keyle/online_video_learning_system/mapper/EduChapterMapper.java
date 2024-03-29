package top.keyle.online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.keyle.online_video_learning_system.entry.EduChapter;

import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_chapter(章节表)】的数据库操作Mapper
* @createDate 2023-01-23 21:04:20
* @Entity service/service_edu.pojo.EduChapter
*/
@Mapper
public interface EduChapterMapper extends BaseMapper<EduChapter> {

    List<EduChapter> selectByCourseId(@Param("courseId") String courseId);

    int deleteByCourseId(@Param("courseId") String courseId);

    Integer selectMaxSortByCourseId(@Param("courseId") String courseId);
}




