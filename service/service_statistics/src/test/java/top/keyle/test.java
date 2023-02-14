package top.keyle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.keyle.Online_video_learning_system.client.UcenterClient;

/**
 * @author TMJIE5200
 * @date 2023-02-14 18:31:31
 * @week 星期二
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    UcenterClient ucenterClient;

    @Test
    public void test1(){
        Integer respBean = ucenterClient.countRegister("2023-02-14");
        System.out.println(respBean);
    }
}
