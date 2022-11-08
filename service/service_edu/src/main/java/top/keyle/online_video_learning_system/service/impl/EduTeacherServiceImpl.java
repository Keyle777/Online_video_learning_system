package top.keyle.online_video_learning_system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.online_video_learning_system.mapper.EduTeacherMapper;
import top.keyle.online_video_learning_system.pojo.EduTeacher;
import top.keyle.online_video_learning_system.service.EduTeacherService;

import java.util.Collection;

/**
* @author TMJIE5200
* @description 针对表【edu_teacher(讲师)】的数据库操作Service实现
* @createDate 2022-11-07 22:05:36
*/
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher>
    implements EduTeacherService {

    @Autowired
    EduTeacherMapper eduTeacherMapper;
    @Override
    public int insertSelective(EduTeacher eduTeacher) {
        return eduTeacherMapper.insertSelective(eduTeacher);
    }

    @Override
    public int insertBatch(Collection<EduTeacher> eduTeacherCollection) {
        return eduTeacherMapper.insertBatch(eduTeacherCollection);
    }
}




