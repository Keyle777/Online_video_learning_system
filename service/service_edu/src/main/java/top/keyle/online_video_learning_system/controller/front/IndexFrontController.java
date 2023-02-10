package top.keyle.online_video_learning_system.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.EduTeacher;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseAndTeacherVO;
import top.keyle.online_video_learning_system.service.EduCourseService;
import top.keyle.online_video_learning_system.service.EduTeacherService;
import top.keyle.universal_tool.RespBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author TMJIE5200
 */
@Api(tags = "前台首页控制器")
@RestController
@RequestMapping("/eduService/indexFront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "查询前8条热门课程，查询前4条讲师")
    @GetMapping("index")
    public RespBean index() {
        //根据id进行降序排列，显示列表之后前8条热门课程记录
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("view_count");
        wrapperCourse.last("limit 8");
        List<EduCourse> courseList = courseService.list(wrapperCourse);
        List<CourseAndTeacherVO> hotCourseTeacherList = new ArrayList<>();
        for (EduCourse eduCourse : courseList) {
            CourseAndTeacherVO teacher = courseService.selectAllByTeacherIdOrderByViewCount(eduCourse.getId());
            hotCourseTeacherList.add(teacher);
        }
        //根据id进行降序排列，显示列表之后前4条讲师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("courseList",courseList);
        hashMap.put("teacherList",teacherList);
        hashMap.put("hotCourseTeacherList",hotCourseTeacherList);
        return RespBean.success(hashMap);
    }
}
