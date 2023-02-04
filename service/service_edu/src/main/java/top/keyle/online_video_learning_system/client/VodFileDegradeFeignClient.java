package top.keyle.online_video_learning_system.client;


import org.springframework.stereotype.Component;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

import java.util.List;

/**
 * @author TMJIE5200
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public RespBean deleteAliVideoByVideoSourceId(String videoSourceId) {
        return RespBean.error(RespBeanEnum.DELETING_VIDEO_FAILED);
    }

    @Override
    public RespBean deleteBatchByVideoSourceIds(List<String> videoIdList) {
        return RespBean.error(RespBeanEnum.DELETING_VIDEO_FAILED);
    }
}
