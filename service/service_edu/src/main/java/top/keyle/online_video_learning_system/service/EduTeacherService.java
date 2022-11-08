package top.keyle.online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import top.keyle.online_video_learning_system.pojo.EduTeacher;

import java.util.Collection;

/**
* @author TMJIE5200
* @description 针对表【edu_teacher(讲师)】的数据库操作Service
* @createDate 2022-11-07 22:05:36
*/
public interface EduTeacherService extends IService<EduTeacher> {
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
