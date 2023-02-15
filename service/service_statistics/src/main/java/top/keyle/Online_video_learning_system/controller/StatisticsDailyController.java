package top.keyle.Online_video_learning_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.Online_video_learning_system.service.StatisticsDailyService;
import top.keyle.universal_tool.RespBean;

import java.util.Map;


/**
 * @author TMJIE5200
 */
@RestController
@RequestMapping("/statisticsService/statistics/")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    /**
     * 统计某一天注册人数，生成统计数据
     * @param day
     * @return
     */
    @GetMapping("registerCount/{day}")
    public RespBean registerCount(@PathVariable String day){
        System.out.println("统计某一天注册人数，生成统计数据");
        staService.registerCount(day);
        return RespBean.success();
    }

    /**
     * 图表显示，返回两部分数据，日期json数组，数量json数组
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("showData/{type}/{begin}/{end}")
    public  RespBean showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return RespBean.success(map);
    }
}

