package top.keyle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.keyle.Online_video_learning_system.client.UcenterClient;

/**
 * @author TMJIE5200
 * @date 2023-02-12 16:57:29
 * @week 星期日
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {

    @Autowired
    private UcenterClient ucenterClient;

    @Test
    public void test1(){
        System.out.println(ucenterClient.getInfo("1623698548130955265"));
    }
}
