package top.keyle.online_video_learning_system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseInfoVO;
import top.keyle.online_video_learning_system.service.EduCourseService;
import top.keyle.universal_tool.RespBean;

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
     * @param courseInfoVO 课程基本信息的表单对象
     * @return 返回添加后的课程信息id
     */
    @PostMapping("/addCourseInfo")
    public RespBean addCourseInfo(
            @ApiParam(name = "CourseInfoVO", value = "课程基本信息的表单对象", required = true) @RequestBody CourseInfoVO courseInfoVO
    ){
        String id = courseService.saveCourseInfo(courseInfoVO);
        return RespBean.success("CourseID:"+id);
    }
}
