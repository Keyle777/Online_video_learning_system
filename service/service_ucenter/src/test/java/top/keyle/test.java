package top.keyle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.keyle.online_video_learning_system.mapper.EduCourseCollectMapper;
import top.keyle.online_video_learning_system.mapper.UcenterMemberMapper;
import top.keyle.online_video_learning_system.service.EduCourseCollectService;
import top.keyle.online_video_learning_system.service.UcenterMemberService;

/**
 * @author TMJIE5200
 * @date 2023-02-09 19:47:57
 * @week 星期四
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    UcenterMemberService memberService;

    @Autowired
    EduCourseCollectMapper eduCourseCollectMapper;
    @Autowired
    UcenterMemberMapper ucenterMemberMapper;

    @Autowired
    EduCourseCollectService eduCourseCollectService;
        // 测试的时候一定要加下面2个。不然会报错找不到memberService
        // @SpringBootTest
        //@RunWith(SpringRunner.class)
    @Test
    public void test1(){
        String id ="1623698548130955265";
        System.out.println(eduCourseCollectMapper.SelectFreeVsPaid(id));
    }
}
