package top.keyle.online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import top.keyle.online_video_learning_system.entry.EduTeacher;
import top.keyle.online_video_learning_system.entry.vo.eduTeacher.EduTeacherQuery;
import top.keyle.universal_tool.JsonPage;

import java.util.Collection;

/**
* @author TMJIE5200
* @description 针对表【edu_teacher(讲师)】的数据库操作Service
* @createDate 2022-11-07 22:05:36
*/
public interface EduTeacherService extends IService<EduTeacher> {
    /**
     * 添加教师
     * @param eduTeacher 讲师对象
     * @return 添加成功返回true，失败返回false
     */
    Boolean insertSelective(EduTeacher eduTeacher);

    /**
     * 批量添加教师
     * @param eduTeacherCollection 讲师对象集合
     * @return 添加成功返回true，失败返回false
     */
    Boolean insertBatch(@Param("eduTeacherCollection") Collection<EduTeacher> eduTeacherCollection);

    /**
     * 分页无搜索条件获取讲师列表
     * @param page 页码
     * @param pageSize 每页显示数据条数
     * @return 讲师列表
     */
    JsonPage<EduTeacher> paginateToGetAListOfInstructors(@Param("page") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * 分页有搜索条件获取讲师列表
     * @param page 页码
     * @param pageSize 每页显示数据条数
     * @param eduTeacherQuery 搜索条件
     * @return 讲师列表
     */
    JsonPage<EduTeacher> getAListOfInstructorsBasedOnCriteria(@Param("page") Integer page, @Param("pageSize") Integer pageSize, @Param("eduTeacherQuery") EduTeacherQuery eduTeacherQuery);

    /**
     * 更新讲师信息
     * @param eduTeacher 更新的讲师表单
     * @return 成功返回true，否则返回false
     */
    Boolean updateSelective(EduTeacher eduTeacher);
}
