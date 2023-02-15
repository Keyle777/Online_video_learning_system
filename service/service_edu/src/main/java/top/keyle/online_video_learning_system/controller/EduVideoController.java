package top.keyle.online_video_learning_system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.online_video_learning_system.entry.vo.video.VideoInfoForm;
import top.keyle.online_video_learning_system.service.EduVideoService;
import top.keyle.universal_tool.RespBean;

import java.util.List;

/**
 * @author TMJIE5200
 * @date 2023-01-23 22:06:02
 * @week 星期一
 */
@RestController
@RequestMapping("/eduService/video")
@Api(tags = {"视频(课时)管理"})
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @ApiOperation(value = "根据章节ID查询其下视频排序最大的数字")
    @GetMapping("/getTheMaximumSort/{chapterId}")
    public RespBean getTheMaximumSort(
            @ApiParam(name = "chapterId", value = "章节ID", required = true)
            @PathVariable String chapterId){
        System.out.println(chapterId);
        Integer result = videoService.getTheMaximumSort(chapterId);
        return RespBean.success("maxSort",result);
    }

    @PostMapping("/addVideo")
    @ApiOperation(value = "新增课时")
    public RespBean addVideo(
            @ApiParam(name = "videoInfoForm", value = "课时对象", required = true)
            @RequestBody VideoInfoForm videoInfoForm) {
        boolean save = videoService.saveVideoInfo(videoInfoForm);
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
    public RespBean getVideoById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id) {
        VideoInfoForm videoInfoForm = videoService.getVideoInfoFormById(id);
        return RespBean.success("item", videoInfoForm);
    }

    /**
     * 修改视频信息
     * @param videoInfoForm 视频表单
     * @return success/error
     */
    @PutMapping("update")
    public RespBean update(
            @ApiParam(name = "VideoInfoForm", value = "课时基本信息", required = true)
            @RequestBody VideoInfoForm videoInfoForm) {
        boolean update = videoService.updateVideoInfoById(videoInfoForm);
        if (update) {
            return RespBean.success();
        } else {
            return RespBean.error();
        }
    }

    @DeleteMapping("{id}")
    public RespBean deleteById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id) {

        boolean result = videoService.removeVideoById(id);
        if(result){
            return RespBean.success();
        }else{
            return RespBean.error();
        }
    }

    @DeleteMapping("/deleteByVideoSourceId/{id}")
    public RespBean deleteByVideoSourceId(
            @ApiParam(name = "id", value = "阿里云视频ID", required = true)
            @PathVariable String id) {

        boolean result = videoService.deleteByVideoSourceId(id);
        if(result){
            return RespBean.success();
        }else{
            return RespBean.error();
        }
    }

    /**
     * 根据视频IDs，批量删除视频
     * @param videoIdList videoIds
     * @return 成功返回success，否则返回error
     */
    @DeleteMapping("deleteBatch")
    public RespBean removeVideoList(
            @ApiParam(name = "videoIdList", value = "云端视频id", required = true)
            @RequestParam("videoIdList") List<String> videoIdList){
        videoService.removeVideoList(videoIdList);
        return RespBean.success();
    }
}
