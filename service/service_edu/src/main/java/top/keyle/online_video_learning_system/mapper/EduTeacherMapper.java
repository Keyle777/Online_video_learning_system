package top.keyle.online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.keyle.online_video_learning_system.pojo.EduTeacher;

import java.util.Collection;

/**
* @author TMJIE5200
* @description 针对表【edu_teacher(讲师)】的数据库操作Mapper
* @createDate 2022-11-07 22:05:36
* @Entity generator.domain.EduTeacher
*/
@Mapper
public interface EduTeacherMapper extends BaseMapper<EduTeacher> {
    /**
     * 添加教师
     * @param eduTeacher
     * @return
     */
    int insertSelective(EduTeacher eduTeacher);

    /**
     * 批量添加教师
     * @param eduTeacherCollection
     * @return
     */
    int insertBatch(@Param("eduTeacherCollection") Collection<EduTeacher> eduTeacherCollection);


}




