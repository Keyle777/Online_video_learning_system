package top.keyle;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.keyle.online_video_learning_system.client.VodClient;
import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.EduVideo;
import top.keyle.online_video_learning_system.mapper.EduCourseMapper;
import top.keyle.online_video_learning_system.mapper.EduVideoMapper;
import top.keyle.online_video_learning_system.service.*;

/**
 * @author TMJIE5200
 * @date 2023-02-05 14:23:25
 * @week 星期日
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test1 {

    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    EduCourseService eduCourseService;

    @Autowired
    EduSubjectService eduSubjectService;

    @Autowired
    EduVideoService eduVideoService;

    @Autowired
    EduCourseMapper eduCourseMapper;
    @Test
    public void tetete(){
        System.out.println(eduCourseService.selectCollectByCourseIdAndMemberId("11", "1623698548130955265"));
    }
    @Test
    public void tect(){
        System.out.println(eduCourseMapper.selectAllByTeacherIdOrderByViewCount("11"));
    }
    @Test
    public void removeChapterById(){
        System.out.println(eduChapterService.removeChapterById("1619732226820886530"));
    }

    @Test
    public void getChapterVideoByCourseId(){
        System.out.println(eduChapterService.getChapterVideoByCourseId("1619720027838697473"));
    }
    @Test
    public void nestedList(){
        System.out.println(eduSubjectService.nestedList());
    }

    @Autowired
    EduVideoMapper eduVideoMapper;
    @Test
    public void getCountByChapterId(){

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", "1619720061850308609");
        // 根据 chapter_id 条件，查询总记录数
        Long count = eduVideoMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    @Autowired
    VodClient vodClient;

    @Test
    public void removeVideoByCourseId(){
            /*eduVideoService.removeVideoByCourseId("1622144153572409345");
            * 这个没问题
            *
            * */

            eduChapterService.removeChapterById("1622205121534935041");

    }

    @Test
    public void deleteVideo(){
        String courseId = "11";
        EduCourse courseInfo = eduCourseService.getById(courseId);
        if (courseInfo != null) {
            Long viewCount = courseInfo.getViewCount();
            courseInfo.setViewCount(viewCount+1);
            eduCourseService.updateById(courseInfo);
        }
        System.out.println(eduCourseService.getById(courseId).getViewCount());
    }
    @Autowired
    EduTeacherService eduTeacherService;


}
