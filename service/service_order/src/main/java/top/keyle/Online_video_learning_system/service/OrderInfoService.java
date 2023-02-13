package top.keyle.Online_video_learning_system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import top.keyle.Online_video_learning_system.entity.OrderInfo;
import top.keyle.Online_video_learning_system.enums.OrderStatus;
import top.keyle.universal_tool.JsonPage;

import java.util.List;

public interface OrderInfoService extends IService<OrderInfo> {
    /**
     * 查询已存在但未支付的订单
     * @param courseId 课程ID
     * @param memberId 用户ID
     * @return 不存在就返回null，存在就返回这条订单记录
     */
    OrderInfo createOrderByProductId(String courseId, String memberId);

    /**
     * 无条件查询所有订单
     * @return
     */
    List<OrderInfo> listOrderByCreateTimeDesc();

    /**
     * 根据商户订单号和给定的订单状态修改订单状态
     * @param orderNo
     * @param orderStatus
     */
    void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus);

    /**
     * 根据订单号获取订单状态
     * @param orderNo 商户订单号
     * @return
     */
    String getOrderStatus(String orderNo);

    /**
     * 查询创建超过minutes分钟并且未支付的订单
     * @param minutes 给定时间
     * @param memberId 用户ID
     * @return
     */
    List<OrderInfo> getNoPayOrderByDuration(int minutes);

    /**
     * 根据订单号获取订单
     * @param orderNo 商户订单号
     * @return
     */
    OrderInfo getOrderByOrderNo(String orderNo);

    JsonPage<OrderInfo> pageListOrders(@Param("page") Integer page, @Param("pageSize") Integer pageSize,String memberId);


    Integer selectCurrentWeekOrder(String memberId);

    Integer selectCurrentDayOrder(String memberId);

    Integer selectCurrentAllOrder(String memberId);
}
