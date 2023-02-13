package top.keyle.Online_video_learning_system.service;

import java.util.Map;

public interface PaymentInfoService {
    /**
     * 生成订单支付状态
     * @param params
     */
    void createPaymentInfoForAliPay(Map<String, String> params);
}
