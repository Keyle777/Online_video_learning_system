package top.keyle.online_video_learning_system.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.EduTeacher;
import top.keyle.online_video_learning_system.service.EduCourseService;
import top.keyle.online_video_learning_system.service.EduTeacherService;
import top.keyle.universal_tool.JsonPage;
import top.keyle.universal_tool.RespBean;

import java.util.HashMap;
import java.util.List;

/**
 * @Author OY
 * @Date 2021/3/30
 */
@RestController
@RequestMapping("/eduService/teacherFront")
@Api(tags = "前端讲师控制器")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 分页查询讲师的方法
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("getTeacherFrontList/{current}/{limit}")
    public RespBean getTeacherFrontList(@PathVariable Integer current, @PathVariable Integer limit){
        // 分页调用
        JsonPage<EduTeacher> jsonPage = eduTeacherService.paginateToGetAListOfInstructors(
                current, limit);
        return RespBean.success(jsonPage);
    }

    @GetMapping("getEduTeacherById/{teacherId}")
    public RespBean getTeacherFrontInfo(@PathVariable String teacherId){
        // 1. 根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);
        // 2. 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = eduCourseService.list(wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("teacher",eduTeacher);
        hashMap.put("courseList",courseList);
        return RespBean.success(hashMap);
    }

}
