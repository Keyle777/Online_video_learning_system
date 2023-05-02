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
    /**
     * 获取数据库中课程表最新的更新时间
     * @return
     */
    Date selectMaxModificationTime();

    /**
     * 批量新增数据和批量修改数据同为此方法
     * @param elasticsearchIndex 索引名
     * @throws IOException
     */
    void modifyTheIndex(String elasticsearchIndex) throws IOException;
}
