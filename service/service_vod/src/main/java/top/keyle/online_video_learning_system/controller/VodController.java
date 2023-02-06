package top.keyle.online_video_learning_system.controller;

import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.keyle.online_video_learning_system.service.VodService;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

import java.util.List;

/**
 * @author TMJIE5200
 */
@RestController
@RequestMapping("/vodService/video")
@CrossOrigin
@Api(tags = {"视频服务管理"})
public class VodController {
    @Autowired
    private VodService vodService;

    /**
     * 根据id获得视频详细信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation(value = "根据课程ID获取视频全部信息")
    public RespBean queryDetailsBasedOnVideoID(@PathVariable String id){
        GetVideoInfoResponse videoInfoResponse = vodService.queryDetailsBasedOnVideoID(id);

        return RespBean.success("videoInfoResponse",videoInfoResponse);
    }


    /**
     * 上传视频到阿里云
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadVideo")
    @ApiOperation(value = "上传视频至阿里云")
    public RespBean uploadAlyVideo(MultipartFile file) {
        // 返回上传视频的id值
        String videoId = vodService.uploadVideo(file);
        return RespBean.success("videoId",videoId);
    }

    /**
     * 删除视频
     * @param videoSourceId 阿里云视频ID
     * @return flag
     */
    @DeleteMapping("{videoSourceId}")
    @ApiOperation(value = "删除视频")
    public RespBean deleteVodById(@PathVariable String videoSourceId) {
        Boolean flag = vodService.deleteVodById(videoSourceId);
        if (flag) {
            return RespBean.success();
        } else {
            return RespBean.error();
        }
    }

    @DeleteMapping("/deleteBatch")
    @ApiOperation(value = "删除多个阿里云视频的方法")
    public RespBean deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        Boolean flag = vodService.removeMoreVideo(videoIdList);
        if (flag) {
            return RespBean.success();
        } else {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
    }

    /**
     * 根据id获取视频凭证
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public RespBean getPlayAuth(@PathVariable String id) {
        String playAuth = vodService.getPlayAuth(id);
        return RespBean.success("playAuth",playAuth);
    }

    @ApiOperation(value = "人工审核通过方法")
    @GetMapping("/CreateAudit/{AuditContent}")
    public RespBean CreateAudit(@PathVariable String AuditContent){
        return RespBean.success("CreateAudit",vodService.CreateAudit(AuditContent));
    }

    @ApiOperation(value = "获取音视频的源文件信息，包括文件地址、分辨率、码率等")
    @GetMapping("/GetMezzanineInfo/{VideoId}")
    public RespBean GetMezzanineInfo(@PathVariable String VideoId){
        return RespBean.success("GetMezzanineInfo",vodService.GetMezzanineInfo(VideoId));
    }

    @ApiOperation(value = "获取音视频的时长")
    @GetMapping("/getVideoDuration/{VideoId}")
    public RespBean getVideoDuration(@PathVariable String VideoId){
        GetMezzanineInfoResponse response = vodService.GetMezzanineInfo(VideoId);
        Double duration = Double.valueOf(response.getMezzanine().getDuration());
        return RespBean.success("duration",duration);
    }

}
