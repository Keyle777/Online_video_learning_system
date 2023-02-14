package top.keyle.Online_video_learning_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.Online_video_learning_system.client.UcenterClient;
import top.keyle.Online_video_learning_system.entity.StatisticsDaily;
import top.keyle.Online_video_learning_system.mapper.StatisticsDailyMapper;
import top.keyle.Online_video_learning_system.service.StatisticsDailyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author TMJIE5200
* @description 针对表【statistics_daily(网站统计每日数据表)】的数据库操作Service实现
* @createDate 2023-02-14 17:40:16
*/
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily>
    implements StatisticsDailyService{
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        // 添加记录之前删除之前相同的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        // 远程调用得到某一天注册人数

        Integer countRegister = ucenterClient.countRegister(day);

        // 把获取数据添加到数据库，统计分析表里面
        StatisticsDaily sta = new StatisticsDaily();
        // 注册人数
        sta.setRegisterNum(countRegister);
        // 统计日期
        sta.setDateCalculated(day);
        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(sta);
    }

    /**
     * 图表显示，返回两部分数据，日期json数组，数量json数组
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        // 根据条件查询对应数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        // 因为返回两部分数据：日期 和 日期对应数量
        // 前端要求数组json结构，对应后端java代码时list集合
        // 创建两个list集合，一个日期list，一个数量list
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> munDataList = new ArrayList<>();

        for(int i =0; i < staList.size(); i++){
            StatisticsDaily daily = staList.get(i);
            // 封装日期list集合
            date_calculatedList.add(daily.getDateCalculated());
            // 封装对应数量
            switch (type){
                case "login_num":
                    munDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    munDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    munDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    munDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        // 把封装之后两个list集合放到map集合，进行返回
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",munDataList);
        return map;
    }
}




