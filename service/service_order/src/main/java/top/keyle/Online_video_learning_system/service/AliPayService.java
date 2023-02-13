package top.keyle.Online_video_learning_system.service;

import java.util.Map;

/**
 * @author TMJIE5200
 */
public interface AliPayService {
    /**
     * 创建交易
     * @param courseId 课程id
     * @param memberId 用户ID
     * @return
     */
    String tradeCreate(String courseId, String memberId);

    /**
     * 处理订单
     * @param params 参数中必须含有订单号
     */
    void processOrder(Map<String, String> params);

    /**
     * 用户取消订单
     * @param orderNo
     */
    void cancelOrder(String orderNo);

    /**
     * 查询订单
     * @param orderNo
     * @return
     */
    String queryOrder(String orderNo);

    /**
     * 检查订单状态
     * @param orderNo
     */
    void checkOrderStatus(String orderNo);

    /**
     * 返回账单下载网址
     * @param billDate 票据日期
     * @param type 票据类型
     * @return
     */
    String queryBill(String billDate, String type);

}
