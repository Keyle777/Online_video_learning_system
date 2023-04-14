package top.keyle.online_video_learning_system.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.online_video_learning_system.client.OrdersClient;
import top.keyle.online_video_learning_system.entry.EduCourse;
import top.keyle.online_video_learning_system.entry.frontvo.CourseWebVo;
import top.keyle.online_video_learning_system.entry.vo.chapter.ChapterVo;
import top.keyle.online_video_learning_system.entry.vo.eduCourse.CourseFrontQuery;
import top.keyle.online_video_learning_system.service.EduChapterService;
import top.keyle.online_video_learning_system.service.EduCourseService;
import top.keyle.universal_tool.JsonPage;
import top.keyle.universal_tool.JwtUtils;
import top.keyle.universal_tool.RespBean;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@Api(tags = "前端课程控制器")
@RequestMapping("/eduService/courseFront")
@SuppressWarnings("all")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    /**
     * 条件查询分页查询课程
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @GetMapping("getFrontCourseList/{page}/{limit}")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "page", example = "1", required = true),
            @ApiImplicitParam(value = "每页条数", name = "limit", example = "5", required = true),
            @ApiImplicitParam(value = "查询条件对象", name = "courseFrontQuery", dataType = "CourseFrontQuery")
    })
    public RespBean getFrontCourseList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Integer page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Integer limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            CourseFrontQuery courseFrontQuery)
    {
        // 以json格式封装得到的课程列表
        JsonPage<EduCourse> jsonPage = courseService
                .getCourseFrontList(page, limit, courseFrontQuery);
        return RespBean.success(jsonPage);
    }

    /**
     * 课程详情的方法
     * @param courseId
     * @return
     */
    @GetMapping("getFrontCourseInfo/{courseId}")
    public RespBean getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        // 根据课程id, 编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        // 根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList =  chapterService.getChapterVideoByCourseId(courseId);
        //根据课程id和用户id查询当前课程是否已经支付过了
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean buyCourse = false;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("courseWebVo",courseWebVo);
        hashMap.put("chapterVideoList",chapterVideoList);
        if(StringUtils.isEmpty(memberId)){
            hashMap.put("isBuy",buyCourse);
            return RespBean.success(hashMap);
        }
        //  返回是否购买过此课程，用来在前端显示
        buyCourse = ordersClient.isBuy(memberId,courseId);
        hashMap.put("isBuy",buyCourse);
        return RespBean.success(hashMap);
    }


    /**
     * 查询指定课程是否已被指定会员收藏
     *
     * @param courseid   课程ID
     * @param memberId   会员ID
     * @return 返回响应结果，包含以下字段：
     *         collectId: 该会员已收藏该课程时的收藏记录ID，若未收藏则为null
     *         IsCollect: 是否已被收藏，true表示已被收藏，false表示未被收藏
     */
    @GetMapping("/selectCollect/{courseid}/{memberId}")
    public RespBean selectCollect(
            @PathVariable String courseid,
            @PathVariable String memberId
    ){
        HashMap<String, Object> hashMap = new HashMap<>();
        String collectId = courseService.selectCollectByCourseIdAndMemberId(courseid, memberId);
        if(collectId != null){
            hashMap.put("collectId",collectId);
            hashMap.put("IsCollect",true);
            return RespBean.success(hashMap);
        }else {
            hashMap.put("collectId",null);
            hashMap.put("IsCollect",false);
            return RespBean.success(hashMap);
        }
    }

    /**

     更新课程购买数量
     @param courseId 课程ID
     @return 返回操作结果，成功返回RespBean.success()
     */
    @GetMapping("updateCourseBuyCount/{courseId}")
    public RespBean updateCourse(@PathVariable String courseId){
        // 根据课程ID获取课程信息
        EduCourse course = courseService.getById(courseId);
        // 将课程购买数量加1
        course.setBuyCount(course.getBuyCount()+1);
        // 构建查询条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("id",courseId);
        // 更新课程购买数量
        courseService.update(course,wrapper);
        return RespBean.success();
    }

    /**
     更新课程的浏览量
     @param courseId 课程id
     @return RespBean 返回操作结果的响应对象
     */
    @GetMapping("updateCourseViewCount/{courseId}")
    public RespBean updateCourseViewCount(@PathVariable String courseId){
        // 获取课程信息
        EduCourse courseInfo = courseService.getById(courseId);
        // 获取课程的浏览量
        Long viewCount = courseInfo.getViewCount();
        if (courseInfo != null) {
            // 将课程的浏览量加1
            courseInfo.setViewCount(viewCount + 1);
            // 更新课程信息
            courseService.updateById(courseInfo);
        }
        return RespBean.success();
    }
}
