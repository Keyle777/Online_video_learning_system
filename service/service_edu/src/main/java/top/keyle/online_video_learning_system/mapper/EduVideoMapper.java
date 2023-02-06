package top.keyle.online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.keyle.online_video_learning_system.entry.EduVideo;

import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_video(课程视频表)】的数据库操作Mapper
* @createDate 2023-01-23 21:11:51
* @Entity service/service_edu.pojo.EduVideo
*/
@Mapper
public interface EduVideoMapper extends BaseMapper<EduVideo> {

    List<EduVideo> selectByChapterId(@Param("chapterId") String chapterId);

    List<EduVideo> selectByCourseId(@Param("courseId") String courseId);

    Integer getTheMaximumSort(@Param("chapterId") String chapterId);
}




