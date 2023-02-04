package top.keyle.online_video_learning_system.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.keyle.online_video_learning_system.service.VodService;
import top.keyle.online_video_learning_system.utils.AliyunVodSDKUtil;
import top.keyle.online_video_learning_system.utils.ConstantVodUtils;
import top.keyle.universal_tool.GlobalException;
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
     *
     * @param videoSourceId
     * @return
     */
    @DeleteMapping("{videoSourceId}")
    @ApiOperation(value = "获取视频播放身份验证")
    public RespBean getVideoPlayAuth(@PathVariable String videoSourceId) {
        Boolean flag = vodService.deleteVodById(videoSourceId);
        if (flag) {
            return RespBean.success();
        } else {
            return RespBean.error();
        }
    }

    @DeleteMapping("deleteBatch")
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
        try {
            // 创建初始化对象
            DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建对象获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 向request设置视频id
            request.setVideoId(id);
            // 调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return RespBean.success("playAuth", playAuth);
        } catch (ClientException e) {
            // 获取凭证失败
            throw new GlobalException(RespBeanEnum.FAILED_TO_GET_CREDENTIALS);
        }
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
