package top.keyle.online_video_learning_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.frontvo.CourseWebVo;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.*;
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
        // 将修改课程表单数据转移到课程对象中，用于更新至数据库
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        // 执行更新课程信息
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


    /**
     * 根据查询条件获取课程列表，并进行分页
     * @param page 当前页码
     * @param pageSize 每页显示条数
     * @param courseQuery 查询条件对象
     * @return 返回查询结果的分页信息
     */
    @Override
    public JsonPage<EduCourse> getAListOfCoursesBasedOnCriteria(
            Integer page,
            Integer pageSize,
            CourseQuery courseQuery) {
        // 开启分页
        PageHelper.startPage(page, pageSize);
        // 构造 Lambda 查询器
        LambdaQueryWrapper<EduCourse> wrapper = new LambdaQueryWrapper<>();
        // 获取查询条件参数
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        String status = courseQuery.getStatus();
        // 设置查询条件
        wrapper.like(!ObjectUtils.isEmpty(title), EduCourse::getTitle, title)
                .eq(!ObjectUtils.isEmpty(teacherId), EduCourse::getTeacherId, teacherId)
                .eq(!ObjectUtils.isEmpty(subjectParentId), EduCourse::getSubjectParentId, subjectParentId)
                .eq(!ObjectUtils.isEmpty(subjectId), EduCourse::getSubjectId, subjectId)
                .orderByDesc(EduCourse::getGmtCreate)
                .eq(!ObjectUtils.isEmpty(status), EduCourse::getStatus, status);
        // 执行查询
        List<EduCourse> eduCourseList = eduCourseMapper.selectList(wrapper);
        // 将查询结果封装为 JsonPage 对象并返回
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
        String searchText = courseQuery.getSearchText();
        String teacherId = courseQuery.getTeacherId();
        wrapper.orderByDesc(!ObjectUtils.isEmpty(buyCountSort), EduCourse::getBuyCount)
                .eq(!ObjectUtils.isEmpty(subjectParentId), EduCourse::getSubjectParentId, subjectParentId)
                .eq(!ObjectUtils.isEmpty(subjectId), EduCourse::getSubjectId, subjectId)
                .orderByDesc(!ObjectUtils.isEmpty(gmtCreateSort),EduCourse::getGmtCreate)
                .orderByDesc(!ObjectUtils.isEmpty(priceSort), EduCourse::getPrice)
                .like(!ObjectUtils.isEmpty(searchText),EduCourse::getTitle,searchText)
                .eq(!ObjectUtils.isEmpty(teacherId),EduCourse::getTeacherId,teacherId);

        List<EduCourse> eduCourseList = eduCourseMapper.selectList(wrapper);
        return JsonPage.restPage(new PageInfo<>(eduCourseList));
    }

    @Override
    public CourseAndTeacherVO selectAllByTeacherIdOrderByViewCount(String courseId) {
        return eduCourseMapper.selectAllByTeacherIdOrderByViewCount(courseId);
    }

    @Override
    public List<EduCourse> SelectCourseListBySearchText(String courseName) {
        return eduCourseMapper.SelectCourseListBySearchText(courseName);
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

    /**
     * 根据课程ID和用户ID查询收藏信息
     *
     * @param courseId 课程ID
     * @param memberId 用户ID
     * @return String 返回收藏信息的ID，若未收藏则返回null
     */
    @Override
    public String selectCollectByCourseIdAndMemberId(String courseId, String memberId) {
        // 构建课程查询条件
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq(!StringUtils.isEmpty(courseId),"id",courseId);
        // 查询课程信息
        EduCourse eduCourse = eduCourseMapper.selectOne(courseQueryWrapper);
        // 如果用户ID为空或课程信息不存在，则返回null
        if(memberId == null || eduCourse==null){
            return null;
        }
        // 返回收藏信息的ID
        return eduCourseMapper.selectCollectByCourseIdAndMemberId(courseId,memberId);
    }
}




