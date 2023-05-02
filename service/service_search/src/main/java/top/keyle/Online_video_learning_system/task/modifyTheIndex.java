package top.keyle.Online_video_learning_system.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.keyle.Online_video_learning_system.service.EduCourseService;

import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class modifyTheIndex {
    @Autowired
    EduCourseService eduCourseService;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastDate = new Date();
    @Scheduled(cron = "0/5 * * * * ?")
    public void verify() {
        Date date = eduCourseService.selectMaxModificationTime();
        if (!lastDate.equals(date)) {
            log.info("正在修改，最后一次更新时间为：" + lastDate);
            try {
                eduCourseService.modifyTheIndex("edu_course");
                lastDate = date;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.info("一切正常，课程表未发生变化，继续运行，最新更新日期为："+lastDate);
        }
    }
}
