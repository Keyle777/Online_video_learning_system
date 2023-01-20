package top.keyle.online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.keyle.online_video_learning_system.entry.EduComment;


/**
* @author TMJIE5200
* @description 针对表【edu_comment(评论表)】的数据库操作Mapper
* @createDate 2023-01-19 23:32:06
* @Entity top.keyle.online_video_learning_system.pojo.EduComment
*/
@Mapper
public interface EduCommentMapper extends BaseMapper<EduComment> {

}




