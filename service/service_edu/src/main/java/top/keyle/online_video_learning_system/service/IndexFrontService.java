package top.keyle.online_video_learning_system.service;


import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.EduTeacher;

import java.util.List;

/**
 * @Author OY
 * @Date 2021/3/26
 */
public interface IndexFrontService {

    /**
     * 查询前8条热门课程
     * @return
     */
    List<EduCourse> getHotCourseList();

    /**
     * 查询前4条名师
     * @return
     */
    List<EduTeacher> getFourTeacherList();
}
