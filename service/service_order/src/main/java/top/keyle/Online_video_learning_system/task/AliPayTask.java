package top.keyle.Online_video_learning_system.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.keyle.Online_video_learning_system.entity.OrderInfo;
import top.keyle.Online_video_learning_system.enums.PayType;
import top.keyle.Online_video_learning_system.service.AliPayService;
import top.keyle.Online_video_learning_system.service.OrderInfoService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class AliPayTask {

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private AliPayService aliPayService;

    /**
     * 从第0秒开始每隔30秒执行1次，查询创建超过5分钟，并且未支付的订单
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void orderConfirm(){

        log.info("orderConfirm 被执行......支付类型"+ PayType.ALIPAY.getType());

        List<OrderInfo> orderInfoList = orderInfoService.getNoPayOrderByDuration(1);

        for (OrderInfo orderInfo : orderInfoList) {
            String orderNo = orderInfo.getOrderNo();
            log.warn("超时订单 ===> {}", orderNo);

            //核实订单状态：调用支付宝查单接口
            aliPayService.checkOrderStatus(orderNo);
        }
    }
}
