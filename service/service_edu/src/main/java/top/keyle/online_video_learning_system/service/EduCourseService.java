package top.keyle.online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseInfoVO;

/**
* @author TMJIE5200
* @description 针对表【edu_course(课程表)】的数据库操作Service
* @createDate 2023-01-22 15:07:00
*/
public interface EduCourseService extends IService<EduCourse> {
    /**
     * 添加课程基本信息
     * @param courseInfoVO 课程基本信息的表单对象
     * @return 返回添加后的课程信息id
     */
    String saveCourseInfo(CourseInfoVO courseInfoVO);
    // 根据课程id查询课程基本信息
    CourseInfoVO getCourseInfo(String courseId);

    // 修改课程信息
    void updateCourseInfo(CourseInfoVO courseInfoVo);

}
