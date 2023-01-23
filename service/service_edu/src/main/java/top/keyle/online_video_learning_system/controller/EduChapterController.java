package top.keyle.online_video_learning_system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.online_video_learning_system.entry.vo.chapter.ChapterVo;
import top.keyle.online_video_learning_system.service.EduChapterService;
import top.keyle.universal_tool.RespBean;

import java.util.List;

@RestController
@RequestMapping("/eduService/chapter")
@CrossOrigin
@Api(tags = {"章节控制器"})
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    /**
     * TODO 根据课程id进行查询
     * @param courseId
     * @return
     */
    @ApiOperation(value = "课程大纲列表")
    @GetMapping("/getChapterVideo")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "课程ID", name = "courseId")
    })
    public RespBean getChapterVideo(String courseId){
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return RespBean.success("allChapterVideo",list);
    }
}
