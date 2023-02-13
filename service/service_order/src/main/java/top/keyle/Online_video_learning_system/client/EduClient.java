package top.keyle.Online_video_learning_system.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.keyle.Online_video_learning_system.client.impl.EduClientImpl;
import top.keyle.Online_video_learning_system.entity.EduCourse;
import top.keyle.Online_video_learning_system.entity.EduTeacher;

@Component
@FeignClient(name = "service-edu", fallback = EduClientImpl.class)
@SuppressWarnings("all")
public interface EduClient {

    //根据课程ID查询课程详细信息
    @GetMapping("/eduService/eduCourse/getCourseInfoFront/{courseId}")
    public EduCourse getCourseInfo(@PathVariable("courseId") String courseId);

    // 根据讲师ID 获取讲师详情
    @GetMapping("/eduService/teacher/getEduTeacherByIdFront/{id}")
    public EduTeacher getEduTeacherByIdFront(@PathVariable("id") String id);

}
