package top.keyle.online_video_learning_system.client;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.keyle.online_video_learning_system.entry.UcenterMember;

@Component

public interface UcenterClient {

    @GetMapping("/educenter/member/getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable("id") String id) ;
}
