package top.keyle.Online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.keyle.Online_video_learning_system.entry.EduCourse;

import java.util.Date;

/**
* @author TMJIE5200
* @description 针对表【edu_course(课程表)】的数据库操作Mapper
* @createDate 2023-05-01 23:24:20
* @Entity top.keyle.Online_video_learning_system.top.keyle.Online_video_learning_system/entry.EduCourse
*/
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    Date selectMaxGmtCreate();
}




