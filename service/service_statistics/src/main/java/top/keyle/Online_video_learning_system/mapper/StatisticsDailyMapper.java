package top.keyle.Online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.keyle.Online_video_learning_system.entity.StatisticsDaily;
import top.keyle.Online_video_learning_system.entity.countTheQuantity;

import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【statistics_daily(网站统计每日数据表)】的数据库操作Mapper
* @createDate 2023-02-14 17:40:16
* @Entity top.keyle.Online_video_learning_system.top.keyle.Online_video_learning_system/entry.StatisticsDaily
*/
@Mapper
public interface StatisticsDailyMapper extends BaseMapper<StatisticsDaily> {

    /**
     * 查看注册人数
     * @param begin
     * @param end
     * @return
     */
    List<countTheQuantity> selectNumberOfEnrollees(@Param("begin") String begin, @Param("end") String end);

    /**
     * 查看登录次数
     * @param begin
     * @param end
     * @return
     */
    List<countTheQuantity> selectNumberOfLogins(String begin,String end);

    /**
     * 查询观看视频次数
     * @param begin
     * @param end
     * @return
     */
    List<countTheQuantity> selectTheNumberOfTimesTheCourseWasPlayed(String begin, String end);

    /**
     * 查询新增课程数
     * @param begin
     * @param end
     * @return
     */
    List<countTheQuantity> selectTheNumberOfNewCourses(String begin,String end);
}




