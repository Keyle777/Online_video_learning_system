package top.keyle.Online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import top.keyle.Online_video_learning_system.entity.StatisticsDaily;
import top.keyle.Online_video_learning_system.entity.countTheQuantity;

import java.util.List;
import java.util.Map;

/**
* @author TMJIE5200
* @description 针对表【statistics_daily(网站统计每日数据表)】的数据库操作Service
* @createDate 2023-02-14 17:40:16
*/
public interface StatisticsDailyService extends IService<StatisticsDaily> {
    /**
     * 统计某一天注册人数，生成统计数据
     * @param day
     */
    void generateStatistics(String day);

    Map<String, Object> getShowData(String type, String begin, String end);

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
