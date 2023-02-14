package top.keyle.Online_video_learning_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.Online_video_learning_system.entity.OrderInfo;
import top.keyle.Online_video_learning_system.entity.PaymentInfo;
import top.keyle.Online_video_learning_system.mapper.PaymentInfoMapper;
import top.keyle.Online_video_learning_system.service.OrderInfoService;
import top.keyle.Online_video_learning_system.service.PaymentInfoService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    @Autowired
    OrderInfoService orderInfoService;
    /**
     * 记录支付日志：支付宝
     * @param params
     */
    @Override
    public void createPaymentInfoForAliPay(Map<String, String> params) {

        log.info("记录支付日志");

        //获取订单号 order_no
        String orderNo = params.get("out_trade_no");
        //业务编号 transaction_id
        String transactionId = params.get("trade_no");
        //交易状态 trade_state
        String tradeStatus = params.get("trade_status");
        //交易金额 total_fee
        String totalAmount = params.get("total_amount");
        // 交易完成时间
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        OrderInfo orderInfo = orderInfoService.getOne(wrapper);
        Date payTimeDate = orderInfo.getGmtModified();
        BigDecimal totalAmountBigDecimal = new BigDecimal(totalAmount);


        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderNo(orderNo);
        paymentInfo.setTransactionId(transactionId);
        // 交易状态 success / error
        paymentInfo.setTradeState(tradeStatus);
        paymentInfo.setTotalFee(totalAmountBigDecimal);
        paymentInfo.setPayTime(payTimeDate);
        Gson gson = new Gson();
        String json = gson.toJson(params, HashMap.class);
        paymentInfo.setAttr(json);

        baseMapper.insert(paymentInfo);
    }
}
