package top.keyle.Online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import top.keyle.Online_video_learning_system.entry.EduComment;
import top.keyle.universal_tool.JsonPage;

/**
* @author TMJIE5200
* @description 针对表【edu_comment(评论表)】的数据库操作Service
* @createDate 2023-02-12 15:03:15
*/
public interface EduCommentService extends IService<EduComment> {

    JsonPage<EduComment> paginateToGetAListOfInstructors(@Param("page") Integer page, @Param("pageSize") Integer pageSize,String id);

    boolean SaveComment(String memberId, EduComment comment);
}
