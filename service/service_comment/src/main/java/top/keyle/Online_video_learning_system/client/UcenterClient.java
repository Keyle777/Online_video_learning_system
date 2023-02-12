package top.keyle.Online_video_learning_system.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.keyle.Online_video_learning_system.client.impl.UcenterClientImpl;
import top.keyle.Online_video_learning_system.entry.UcenterMember;

@Component
@FeignClient(value = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {

    @GetMapping("/eduTraineeCenter/member/getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable("id") String id) ;
}
