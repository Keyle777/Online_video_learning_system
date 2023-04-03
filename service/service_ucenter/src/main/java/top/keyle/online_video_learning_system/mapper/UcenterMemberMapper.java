package top.keyle.online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.keyle.online_video_learning_system.entity.UcenterMember;
import top.keyle.online_video_learning_system.entity.vo.courseVO.courseVO;

import java.util.List;

@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 统计某一天注册的人数
     * @param day
     * @return
     */
    Integer countRegisterDay(String day);

    /**
     * 统计视频播放总次数
     * @param day
     * @return
     */
    Integer countVideoPlay(String day);

    /**
     * 统计当天新上传的课程数
     * @param day
     * @return
     */
    Integer countCourseInsert(String day);
    /**
     * 学员登录数统计
     */
    Integer countLogin(String day);
    /**
     * 查询用户购买的付费的课程
     * @param id
     * @return
     */
    List<courseVO> selectCourseTostudy(String id);

    int updateLoginCountById(@Param("id") String id);
}
