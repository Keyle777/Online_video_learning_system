package top.keyle.Online_video_learning_system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.keyle.Online_video_learning_system.entity.EduCourse;
import top.keyle.Online_video_learning_system.entity.OrderInfo;

/**
 * @author TMJIE5200
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    EduCourse selectCourseByCourseId(String id);

    Integer selectCurrentMonthOrder(String memberId);

    Integer selectCurrentDayOrder(String memberId);

    Integer selectCurrentAllOrder(String memberId);
}
