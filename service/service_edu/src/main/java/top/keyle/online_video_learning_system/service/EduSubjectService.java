package top.keyle.online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import top.keyle.online_video_learning_system.entry.EduSubject;
import top.keyle.online_video_learning_system.entry.vo.subject.OneSubject;

import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_subject(课程科目表)】的数据库操作Service
* @createDate 2023-01-20 13:45:53
*/

public interface EduSubjectService extends IService<EduSubject> {
    //添加课程分类
    void savaSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> nestedList();
}
