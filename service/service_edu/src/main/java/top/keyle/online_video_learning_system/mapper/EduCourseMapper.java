package top.keyle.online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.keyle.online_video_learning_system.entry.EduCourse;

/**
* @author TMJIE5200
* @description 针对表【edu_course(课程表)】的数据库操作Mapper
* @createDate 2023-01-22 15:07:00
* @Entity service/service_edu.pojo.EduCourse
*/
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {

}




