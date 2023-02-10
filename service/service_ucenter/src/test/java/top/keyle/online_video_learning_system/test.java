package top.keyle.online_video_learning_system;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.keyle.online_video_learning_system.entity.UcenterMember;
import top.keyle.online_video_learning_system.service.UcenterMemberService;

/**
 * @author TMJIE5200
 * @date 2023-02-09 19:47:57
 * @week 星期四
 */
@SpringBootTest
public class test {
    @Autowired
    UcenterMemberService memberService;

    @Test
    public void test1(){
        UcenterMember ucenterMember = memberService.getById("1623698548130955265");
        UcenterMember memeber = new UcenterMember();
        BeanUtils.copyProperties(ucenterMember,memeber);
        System.out.println(memeber);
    }
}
