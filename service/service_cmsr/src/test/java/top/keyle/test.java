package top.keyle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.keyle.Online_video_learning_system.entry.CrmBanner;
import top.keyle.Online_video_learning_system.service.CrmBannerService;

/**
 * @author TMJIE5200
 * @date 2023-02-07 23:06:28
 * @week 星期二
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    CrmBannerService crmBannerService;

    @Test
    public void test1(){
        CrmBanner crmBanner = new CrmBanner();
        crmBanner.setImageUrl("测试");
        crmBanner.setSort(0);
        crmBannerService.save(crmBanner);
        System.out.println(crmBanner);
    }
}
