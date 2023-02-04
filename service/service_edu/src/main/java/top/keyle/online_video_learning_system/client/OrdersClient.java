package top.keyle.online_video_learning_system.client;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component

public interface OrdersClient {

    //根据课程id和用户id查询订单表中订单状态
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
