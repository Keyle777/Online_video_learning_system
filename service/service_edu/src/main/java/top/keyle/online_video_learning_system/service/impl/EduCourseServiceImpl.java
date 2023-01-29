package top.keyle.online_video_learning_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseInfoVO;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CoursePublishVo;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseQuery;
import top.keyle.online_video_learning_system.mapper.EduCourseMapper;
import top.keyle.online_video_learning_system.service.EduChapterService;
import top.keyle.online_video_learning_system.service.EduCourseService;
import top.keyle.online_video_learning_system.service.EduVideoService;
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
        System.out.println("我是根据课程id查询课程基本信息" + eduCourse);
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
        Integer count = baseMapper.updateById(course);
        return count > 0;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (courseQuery == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
        }
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!ObjectUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!ObjectUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!ObjectUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }
        if (!ObjectUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public boolean removeCourseById(String id) {
        //根据课程Id删除所有视频
        eduVideoService.removeByCourseId(id);
        //根据课程Id删除所有章节
        eduChapterService.removeChapterById(id);
        Integer result = baseMapper.deleteById(id);
        return result > 0;
    }
}




