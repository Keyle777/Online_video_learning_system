package top.keyle.Online_video_learning_system.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.keyle.Online_video_learning_system.client.impl.MemberClientImpl;
import top.keyle.Online_video_learning_system.entity.UcenterMember;

@Component
@FeignClient(name = "service-ucenter", fallback = MemberClientImpl.class)
@SuppressWarnings("all")
public interface MemberClient {

    // 根据用户ID 获取用户信息
    @GetMapping("/eduTraineeCenter/member/getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable("id") String id);


}
