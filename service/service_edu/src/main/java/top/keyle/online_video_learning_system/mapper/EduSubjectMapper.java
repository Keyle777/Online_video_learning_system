package top.keyle.online_video_learning_system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.keyle.online_video_learning_system.entry.EduSubject;

/**
* @author TMJIE5200
* @description 针对表【edu_subject(课程科目表)】的数据库操作Mapper
* @createDate 2023-01-20 13:45:53
* @Entity online_video_learning_system.pojo.EduSubject
*/
@Mapper
public interface EduSubjectMapper extends BaseMapper<EduSubject> {

}




