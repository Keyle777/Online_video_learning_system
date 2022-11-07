package top.keyle.online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.keyle.online_video_learning_system.pojo.FileDetail;

/**
* @author TMJIE5200
* @description 针对表【file_detail(文件记录表)】的数据库操作Mapper
* @createDate 2022-11-04 22:45:07
* @Entity generator.domain.FileDetail
*/

@Mapper
public interface FileDetailMapper extends BaseMapper<FileDetail> {

}




