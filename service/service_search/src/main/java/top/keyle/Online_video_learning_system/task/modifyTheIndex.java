package top.keyle.Online_video_learning_system.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.keyle.Online_video_learning_system.service.EduCourseService;

import java.util.Date;

@Component
@Slf4j
public class modifyTheIndex {
    @Autowired
    EduCourseService eduCourseService;
    private Date lastDate = new Date(0);
    @Scheduled(cron = "0/5 * * * * ?")
    public void verify(){
        Date date = eduCourseService.selectMaxModificationTime();

        if(lastDate !=date){
            log.info("正在修改索引，最后一次更新时间为："+ lastDate);
            eduCourseService.modifyTheIndex();
            lastDate = date;
        }
        log.info("一切正常，课程表未发生变化，继续运行。");
    }
}
