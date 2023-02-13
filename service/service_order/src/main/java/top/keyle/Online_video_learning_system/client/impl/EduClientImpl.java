package top.keyle.Online_video_learning_system.client.impl;

import org.springframework.stereotype.Component;
import top.keyle.Online_video_learning_system.client.EduClient;
import top.keyle.Online_video_learning_system.entity.EduCourse;
import top.keyle.Online_video_learning_system.entity.EduTeacher;


@Component
public class EduClientImpl implements EduClient {


    @Override
    public EduCourse getEduCourseByIdFrontOther(String id) {
        System.out.println(id);
        return null;
    }

    @Override
    public EduTeacher getEduTeacherByIdFront(String id) {
        return null;
    }
}
