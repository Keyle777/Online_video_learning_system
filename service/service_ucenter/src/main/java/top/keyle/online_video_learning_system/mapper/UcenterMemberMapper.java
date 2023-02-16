package top.keyle.online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.keyle.online_video_learning_system.entity.UcenterMember;
import top.keyle.online_video_learning_system.entity.vo.courseVO.courseVO;

import java.util.List;

@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegisterDay(String day);

    /**
     * 查询用户购买的付费的课程
     * @param id
     * @return
     */
    List<courseVO> selectCourseTostudy(String id);
}
