package top.keyle.online_video_learning_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseInfoVO;
import top.keyle.online_video_learning_system.mapper.EduCourseMapper;
import top.keyle.online_video_learning_system.service.EduCourseService;
import top.keyle.universal_tool.GlobalException;
import top.keyle.universal_tool.RespBeanEnum;

/**
* @author TMJIE5200
* @description 针对表【edu_course(课程表)】的数据库操作Service实现
* @createDate 2023-01-22 15:07:00
*/
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse>
    implements EduCourseService {
    @Autowired
    EduCourseMapper eduCourseMapper;
    /**
     * 添加课程基本信息
     * @param courseInfoVO 课程基本信息的表单对象
     * @return 课程id
     */
    @Override
    public String saveCourseInfo(CourseInfoVO courseInfoVO) {
        // 向课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        if(!this.save(eduCourse)) {
            // 添加失败
            throw new GlobalException(RespBeanEnum.COURSE_ADDITION_FAILED);
        }
        return eduCourse.getId();
    }
}




