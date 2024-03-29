package top.keyle.online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.frontvo.CourseWebVo;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseAndTeacherVO;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CoursePublishVo;

import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_course(课程表)】的数据库操作Mapper
* @createDate 2023-01-22 15:07:00
* @Entity service/service_edu.pojo.EduCourse
*/
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishVoById(String id);

    CourseAndTeacherVO selectAllByTeacherIdOrderByViewCount(String courseId);

    List<EduCourse> SelectCourseListBySearchText(String courseName);

    CourseWebVo getBaseCourseInfo(String courseId);

    String selectCollectByCourseIdAndMemberId(String courseId, String memberId);

}




