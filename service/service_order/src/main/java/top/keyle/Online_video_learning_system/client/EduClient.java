package top.keyle.Online_video_learning_system.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.keyle.Online_video_learning_system.client.impl.EduClientImpl;
import top.keyle.Online_video_learning_system.entity.EduCourse;
import top.keyle.Online_video_learning_system.entity.EduTeacher;
import top.keyle.universal_tool.RespBean;

@Component
@FeignClient(name = "service-edu", fallback = EduClientImpl.class)
public interface EduClient {

    //根据课程ID查询课程详细信息
    @GetMapping("/eduService/eduCourse/getEduCourseByIdFrontOther/{id}")
    public EduCourse getEduCourseByIdFrontOther(@PathVariable String id);
    // 根据讲师ID 获取讲师详情
    @GetMapping("/eduService/teacher/getEduTeacherByIdFront/{id}")
    public EduTeacher getEduTeacherByIdFront(@PathVariable String id);


    @GetMapping("/eduService/courseFront/updateCourseBuyCount/{courseId}")
    public RespBean updateCourse(@PathVariable String courseId);
}
