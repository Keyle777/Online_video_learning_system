package top.keyle.online_video_learning_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseFrontQuery;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseInfoVO;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CoursePublishVo;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseQuery;
import top.keyle.online_video_learning_system.mapper.EduCourseMapper;
import top.keyle.online_video_learning_system.service.EduChapterService;
import top.keyle.online_video_learning_system.service.EduCourseService;
import top.keyle.online_video_learning_system.service.EduVideoService;
import top.keyle.universal_tool.GlobalException;
import top.keyle.universal_tool.JsonPage;
import top.keyle.universal_tool.RespBeanEnum;

import java.math.BigDecimal;
import java.util.List;

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
    @Autowired
    EduVideoService eduVideoService;
    @Autowired
    EduChapterService eduChapterService;
    /**
     * 添加课程基本信息
     *
     * @param courseInfoVO 课程基本信息的表单对象
     * @return 课程id
     */
    @Override
    public String saveCourseInfo(CourseInfoVO courseInfoVO) {
        // 向课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        if (!this.save(eduCourse)) {
            // 添加失败
            throw new GlobalException(RespBeanEnum.COURSE_ADDITION_FAILED);
        }
        return eduCourse.getId();
    }

    // 根据课程id查询课程基本信息
    @Override
    public CourseInfoVO getCourseInfo(String courseId) {

        // 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVO courseInfoVo = new CourseInfoVO();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        return courseInfoVo;
    }

    // 修改课程信息
    @Override
    public void updateCourseInfo(EduCourse eduCourse, CourseInfoVO courseInfoVo) {
        // 资源 目标
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GlobalException(RespBeanEnum.FAILED_TO_MODIFY_COURSE);
        }
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return eduCourseMapper.getCoursePublishVoById(id);
    }

    @Override
    public Boolean publishCourseById(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus(EduCourse.COURSE_NORMAL);
        return baseMapper.updateById(course) > 0;
    }

    @Override
    public Boolean removeCourseById(String id) {
        //1、根据课程Id删除chapter记录
        eduChapterService.removeChapterByCourseId(id);
        //2、删除完所有依赖课程的记录后，删除课程。
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public JsonPage<EduCourse> getAListOfCoursesBasedOnCriteria(
            Integer page,
            Integer pageSize,
            CourseQuery courseQuery) {

        PageHelper.startPage(page, pageSize);
        LambdaQueryWrapper<EduCourse> wrapper = new LambdaQueryWrapper<>();
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        String status = courseQuery.getStatus();
        wrapper.like(!ObjectUtils.isEmpty(title), EduCourse::getTitle, title)
                .eq(!ObjectUtils.isEmpty(teacherId), EduCourse::getTeacherId, teacherId)
                .ge(!ObjectUtils.isEmpty(subjectParentId), EduCourse::getSubjectParentId, subjectParentId)
                .ge(!ObjectUtils.isEmpty(subjectId), EduCourse::getSubjectId, subjectId)
                .orderByDesc(EduCourse::getGmtCreate)
                .eq(!ObjectUtils.isEmpty(status), EduCourse::getStatus, status);

        List<EduCourse> eduCourseList = eduCourseMapper.selectList(wrapper);
        return JsonPage.restPage(new PageInfo<>(eduCourseList));
    }

    @Override
    public JsonPage<EduCourse> getCourseFrontList(Integer page, Integer pageSize, CourseFrontQuery courseQuery) {
        PageHelper.startPage(page, pageSize);
        LambdaQueryWrapper<EduCourse> wrapper = new LambdaQueryWrapper<>();
        Long buyCountSort = courseQuery.getBuyCountSort();
        String gmtCreateSort = courseQuery.getGmtCreateSort();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        BigDecimal priceSort = courseQuery.getPriceSort();
        wrapper.orderByDesc(!ObjectUtils.isEmpty(buyCountSort), EduCourse::getBuyCount)
                .eq(!ObjectUtils.isEmpty(subjectParentId), EduCourse::getSubjectParentId, subjectParentId)
                .eq(!ObjectUtils.isEmpty(subjectId), EduCourse::getSubjectId, subjectId)
                .orderByDesc(!ObjectUtils.isEmpty(gmtCreateSort),EduCourse::getGmtCreate)
                .orderByDesc(!ObjectUtils.isEmpty(priceSort), EduCourse::getPrice);

        List<EduCourse> eduCourseList = eduCourseMapper.selectList(wrapper);
        return JsonPage.restPage(new PageInfo<>(eduCourseList));
    }
}




