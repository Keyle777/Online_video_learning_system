package top.keyle.online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import top.keyle.online_video_learning_system.entity.EduCourseCollect;
import top.keyle.online_video_learning_system.entity.vo.collectionVO.CollectDetail;
import top.keyle.online_video_learning_system.entity.vo.courseVO.courseVO;
import top.keyle.universal_tool.JsonPage;

import java.util.HashMap;
import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_course_collect(课程收藏)】的数据库操作Service
* @createDate 2023-02-11 18:14:01
*/
public interface EduCourseCollectService extends IService<EduCourseCollect> {
    /**
     * 查询当天的收藏个数
     * @param memberid 用户ID
     * @return
     */
    Integer selectDayCollectionCount(String memberid);

    /**
     * 查询一周内的收藏个数
     * @param memberid 用户ID
     * @return
     */
    Integer SelectNumberOfCollectionsInAWeek(String memberid);

    /**
     * 查询收藏的课程的免费付费比
     * @param memberid 用户ID
     * @return
     */
    HashMap<String,Object> SelectFreeVsPaid(String memberid);

    /**
     * 加载出收藏详情列表
     * @param memberid 用户ID
     * @return
     */
    List<CollectDetail> selectCollectList(String memberid);


    JsonPage<CollectDetail> paginateToGetAListOfInstructors(@Param("page") Integer page, @Param("pageSize") Integer pageSize,String id);

    Integer saveEduCourseCollect(EduCourseCollect eduCourseCollect);

    JsonPage<courseVO> selectCourseCollectionTostudy(@Param("page") Integer page, @Param("pageSize") Integer pageSize,String id);
}
