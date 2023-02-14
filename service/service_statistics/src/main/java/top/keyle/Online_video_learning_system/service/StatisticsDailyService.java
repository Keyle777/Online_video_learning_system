package top.keyle.Online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.keyle.Online_video_learning_system.entity.StatisticsDaily;

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
    void registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
