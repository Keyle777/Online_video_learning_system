package top.keyle.online_video_learning_system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.universal_tool.RespBean;

/**
 * @author TMJIE5200
 * @date 2023-01-23 22:06:02
 * @week 星期一
 */
@RestController
@RequestMapping("/eduService/video")
@CrossOrigin
@Api(tags = {"视频控制器"})
public class EduVideoController {

    @ApiOperation(value = "课程大纲列表")
    @RequestMapping("/addVideo")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "课程ID", name = "courseId")
    })
    public RespBean addVideo(String courseId){
        return RespBean.success();
    }
}
