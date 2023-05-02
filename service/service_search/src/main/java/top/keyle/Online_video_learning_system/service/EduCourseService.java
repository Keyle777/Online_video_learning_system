package top.keyle.Online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.keyle.Online_video_learning_system.entry.EduCourse;

import java.io.IOException;
import java.util.Date;

/**
* @author TMJIE5200
* @description 针对表【edu_course(课程表)】的数据库操作Service
* @createDate 2023-05-01 23:24:20
*/
public interface EduCourseService extends IService<EduCourse> {
    Date selectMaxModificationTime();
    void modifyTheIndex(String elasticsearchIndex) throws IOException;
}
