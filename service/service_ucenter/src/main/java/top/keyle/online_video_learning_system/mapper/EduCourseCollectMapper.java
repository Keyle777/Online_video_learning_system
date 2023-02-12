package top.keyle.online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.keyle.online_video_learning_system.entity.EduCourseCollect;
import top.keyle.online_video_learning_system.entity.vo.collectionVO.CollectDetail;
import top.keyle.online_video_learning_system.entity.vo.collectionVO.NumberOfFavorites;

import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_course_collect(课程收藏)】的数据库操作Mapper
* @createDate 2023-02-11 18:14:01
* @Entity service.service_ucenter.domain.EduCourseCollect
*/
@Mapper
@SuppressWarnings("all")
public interface EduCourseCollectMapper extends BaseMapper<EduCourseCollect> {
    Integer selectDayCollectionCoutn(String memberid);

    Integer SelectNumberOfCollectionsInAWeek(String memberid);

    List<NumberOfFavorites> SelectFreeVsPaid(String memberid);

    List<CollectDetail> selectCollectList(String memberid);

    Integer saveEduCourseCollect(EduCourseCollect eduCourseCollect);

}




