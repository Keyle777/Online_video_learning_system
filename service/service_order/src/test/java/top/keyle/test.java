package top.keyle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.keyle.Online_video_learning_system.client.EduClient;
import top.keyle.Online_video_learning_system.client.MemberClient;
import top.keyle.Online_video_learning_system.service.OrderInfoService;

/**
 * @author TMJIE5200
 * @date 2023-02-13 20:24:52
 * @week 星期一
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    EduClient eduClient;

    @Autowired
    MemberClient memberClient;

    @Autowired
    OrderInfoService orderInfoService;
    @Test
    public void test1(){
        System.out.println(eduClient.getEduTeacherByIdFront("1189389726308478976"));
        System.out.println(memberClient.getInfo("1623698548130955265"));
    }
}
