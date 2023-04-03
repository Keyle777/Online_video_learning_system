package top.keyle.Online_video_learning_system.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.keyle.Online_video_learning_system.client.impl.UcenterClientImpl;
import top.keyle.universal_tool.RespBean;

@Component
@FeignClient(value = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @GetMapping("/eduTraineeCenter/member/countRegister/{day}")
    public Integer countRegister(@PathVariable("day") String day);

    @GetMapping("/eduTraineeCenter/member/statistics/{day}")
    public RespBean statistics(@PathVariable("day") String day);
}
