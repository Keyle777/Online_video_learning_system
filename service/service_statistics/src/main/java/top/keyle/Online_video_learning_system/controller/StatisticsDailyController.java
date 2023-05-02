package top.keyle.Online_video_learning_system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.Online_video_learning_system.service.StatisticsDailyService;
import top.keyle.universal_tool.RespBean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author TMJIE5200
 */
@RestController
@RequestMapping("/statisticsService/statistics/")
@Slf4j
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    public static List<String> getDatesInRange(String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        List<String> dates = new ArrayList<>();
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            dates.add(current.toString());
            current = current.plusDays(1);
        }
        return dates;
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

    /**
     * 统计某一天注册人数，生成统计数据
     * @param day
     * @return
     */
    @GetMapping("generateData/{day}")
    public RespBean generateData(@PathVariable ArrayList<String> day){
        log.info("正在统计日期范围在：" + day.get(0)+"——"+day.get(1) + "之间的数据");
        List<String> dates = getDatesInRange(day.get(0), day.get(1));
        dates.forEach(s->staService.generateStatistics(s));
        log.info("成功生成"+ day.get(0)+"——"+day.get(1) + "之间的数据");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("dates",dates);
        Map<String, Object> registerNum = staService.getShowData("register_num", day.get(0), day.get(1));
        hashMap.put("register_num",registerNum);
        return RespBean.success(hashMap);
    }
}

