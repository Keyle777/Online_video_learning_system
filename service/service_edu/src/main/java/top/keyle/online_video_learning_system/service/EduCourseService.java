package top.keyle.online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseFrontQuery;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseInfoVO;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CoursePublishVo;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseQuery;
import top.keyle.universal_tool.JsonPage;

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

    /**
     * 根据课程id查询课程基本信息
     * @param courseId 课程id
     * @return CourseInfoVO(课程详情表单)
     */
    CourseInfoVO getCourseInfo(String courseId);

    /**
     * 修改课程信息
     * @param eduCourse 课程对象
     * @param courseInfoVo 课程详情表单对象
     */
    void updateCourseInfo(EduCourse eduCourse,CourseInfoVO courseInfoVo);


    /**
     * 根据课程ID获取课程发布信息
     * @param id 课程ID
     * @return CoursePublishVo 发布课程表单对象
     */
    CoursePublishVo getCoursePublishVoById(String id);

    /**
     * 根据课程id发布课程
     * @param id 课程id
     * @return 发布成功返回true,失败返回false
     */
    Boolean publishCourseById(String id);

    /**
     * 根据课程id删除课程记录
     * @param id 课程id
     * @return 成功返回true，否则返回false
     */
    Boolean removeCourseById(String id);

    /**
     * 根据分页条件获取课程列表
     * @param page 页码
     * @param pageSize 每页展示条数
     * @param courseQuery 搜索条件对象
     * @return 课程列表
     */
    JsonPage<EduCourse> getAListOfCoursesBasedOnCriteria(
            @Param("page") Integer page,
            @Param("pageSize") Integer pageSize,
            @Param("courseQuery") CourseQuery courseQuery);

    /**
     * 只要排序的为空就是不执行那个排序的desc，只要不是空就执行
     * @param page
     * @param pageSize
     * @param courseQuery
     * @return
     */
    JsonPage<EduCourse> getCourseFrontList(
            @Param("page") Integer page,
            @Param("pageSize") Integer pageSize,
            @Param("courseQuery") CourseFrontQuery courseQuery);
}
