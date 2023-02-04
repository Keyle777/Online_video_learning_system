package top.keyle.online_video_learning_system.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.keyle.universal_tool.RespBean;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
@SuppressWarnings("all")
public interface VodClient {

    /**
     * 根据视频ID删除阿里云视频
     * @param videoSourceId @PathVariable注解一定要指定参数名称，否则出错
     * @return
     */
    @DeleteMapping("/vodService/video/{videoSourceId}")
    public RespBean deleteAliVideoByVideoSourceId(@PathVariable("videoSourceId") String videoSourceId);

    /**
     * 批量删除阿里云视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/vodService/video/deleteBatch")
    public RespBean deleteBatchByVideoSourceIds(@RequestParam("videoIdList") List<String> videoIdList);

}
