package top.keyle.online_video_learning_system.client;


import org.springframework.stereotype.Component;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

import java.util.List;

/**
 * 熔断器执行开始，连接超时则返回下面结果
 * @author TMJIE5200
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public RespBean deleteAliVideoByVideoSourceId(String videoSourceId) {
        return RespBean.error(RespBeanEnum.CONNECTION_TIMED_OUT);
    }

    @Override
    public RespBean deleteBatchByVideoSourceIds(List<String> videoIdList) {
        return RespBean.error(RespBeanEnum.CONNECTION_TIMED_OUT);
    }
}
