package top.keyle.online_video_learning_system.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.online_video_learning_system.entry.EduVideo;
import top.keyle.online_video_learning_system.service.EduVideoService;
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

    @Autowired
    private EduVideoService videoService;
/**/
    @PostMapping("save")
    public RespBean save(@RequestBody EduVideo video) {
        boolean save = videoService.save(video);
        if (save) {
            return RespBean.success();
        } else {
            return RespBean.error();
        }
    }

    /**
     * 根据ID查询Video对象的回显
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public RespBean getVideoById(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        return RespBean.success("video", video);
    }

    /**
     * 修改
     *
     * @param video
     * @return
     */
    @PutMapping("update")
    public RespBean update(@RequestBody EduVideo video) {
        boolean update = videoService.updateById(video);
        if (update) {
            return RespBean.success();
        } else {
            return RespBean.error();
        }
    }

    /*@DeleteMapping("{id}")
    public RespBean deleteById(@PathVariable String id) {
        // 根据小节id获取视频id，调用方法实现视频删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();

        // 判断小节里面是否有视频id
        if(videoSourceId.isEmpty()){
            // 根据视频id,远程调用实现视频删除
            RespBean result = vodClient.getVideoPlayAuth(videoSourceId);
            if(result.getCode() == 20001){
                throw new GlobalException(RespBeanEnum.DELETING_VIDEO_FAILED);
            }
        }

        // 删除小节
        Boolean flag = videoService.removeVideoById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }*/
}
