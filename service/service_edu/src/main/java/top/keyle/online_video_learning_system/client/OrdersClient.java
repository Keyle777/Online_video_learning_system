package top.keyle.online_video_learning_system.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.keyle.online_video_learning_system.client.impl.OrdersClientImpl;

@Component
@FeignClient(name = "service-order", fallback = OrdersClientImpl.class)
public interface OrdersClient {

    //根据课程id和用户id查询订单表中订单状态
    @GetMapping("/orderService/order-info/isBuy/{memberId}/{courseId}")
    public Boolean isBuy(@PathVariable("memberId") String memberId,@PathVariable("courseId") String courseId);
}
