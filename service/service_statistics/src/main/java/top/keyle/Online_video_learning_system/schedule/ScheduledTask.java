package top.keyle.Online_video_learning_system.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.keyle.Online_video_learning_system.service.StatisticsDailyService;
import top.keyle.Online_video_learning_system.utils.DateUtil;

import java.util.Date;


/**
 * 定时任务
 * @author TMJIE5200
 */
@Component
@Slf4j
public class ScheduledTask {

    private StatisticsDailyService staService;

    /**
     * 0/5 * * * * ? 表示每个5秒执行一次这个方法
     */
/*    @Scheduled(cron = "0/5 * * * * ?")
    public void task1(){
        System.out.println("********task1执行了....");
    }*/

    /**
     * 在每天凌晨1点，把前一天数据进行查询添加
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2(){
        log.info("registerCount 被执行......统计注册每日人数");
        staService.generateStatistics(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
