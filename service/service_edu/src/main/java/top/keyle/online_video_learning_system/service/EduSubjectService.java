package top.keyle.online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import top.keyle.online_video_learning_system.entry.EduSubject;
import top.keyle.online_video_learning_system.entry.vo.subject.OneSubject;

import java.util.List;
import java.util.Map;

/**
* @author TMJIE5200
* @description 针对表【edu_subject(课程科目表)】的数据库操作Service
* @createDate 2023-01-20 13:45:53
*/

public interface EduSubjectService extends IService<EduSubject> {
    /**
     * 添加记录在课程科目表中
     * @param file excel文件
     * @param eduSubjectService
     */
    void savaSubject(MultipartFile file,EduSubjectService eduSubjectService);

    /**
     * 获取分类用户展示树状图
     * @return
     */
    Map<String, Object> nestedList();

    /**
     * 获取分类
     * @return
     */
    List<OneSubject> nestedListTwo();

    void deleteAll();
}
