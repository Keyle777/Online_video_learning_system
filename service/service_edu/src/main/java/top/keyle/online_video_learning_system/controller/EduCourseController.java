package top.keyle.online_video_learning_system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseInfoVO;
import top.keyle.online_video_learning_system.service.EduCourseService;
import top.keyle.universal_tool.GlobalException;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

import java.util.HashMap;

/**
 * @author TMJIE5200
 * @date 2023-01-22 15:11:14
 * @week 星期日
 */
@RestController
@RequestMapping("/eduService/eduCourse")
@Api(tags = {"教育课程控制器"})
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    /**
     * 添加课程基本信息
     *
     * @param courseInfoVO 课程基本信息的表单对象
     * @return 返回添加后的课程信息id
     */
    @PostMapping("/addCourseInfo")
    @ApiOperation(value = "添加课程信息")
    public RespBean addCourseInfo(
            @ApiParam(name = "CourseInfoVO", value = "课程基本信息的表单对象", required = true) @RequestBody CourseInfoVO courseInfoVO
    ) {
        String courseID = courseService.saveCourseInfo(courseInfoVO);
        if (!courseID.isEmpty()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("CourseId", courseID);
            return RespBean.success(hashMap);
        } else {
            return RespBean.error(RespBeanEnum.COURSE_ADDITION_FAILED);
        }
    }

    // 根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    @ApiOperation(value = "根据课程ID获取课程信息")
    public RespBean getCourseInfo(@PathVariable String courseId) {
        EduCourse course = courseService.getById(courseId);
        if (course == null) {
            throw new GlobalException(RespBeanEnum.SELECT_ERROR);
        }
        // 返回表单信息，不必返回整个course课程信息。
        CourseInfoVO courseInfoVo = courseService.getCourseInfo(courseId);
        return RespBean.success("courseInfoVo", courseInfoVo);
    }

    // 根据ID修改课程信息
    @ApiOperation(value = "更新课程")
    @PostMapping("updateCourseInformationByID/{id}")
    public RespBean updateCourseInformationByID(
            @ApiParam(name = "courseInfo", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVO courseInfo,
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        EduCourse eduCourse = courseService.getById(id);
        courseService.updateCourseInfo(eduCourse,courseInfo);
        return RespBean.success();
    }
}
