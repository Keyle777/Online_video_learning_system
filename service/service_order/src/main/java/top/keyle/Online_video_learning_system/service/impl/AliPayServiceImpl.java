package top.keyle.Online_video_learning_system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.keyle.Online_video_learning_system.client.EduClient;
import top.keyle.Online_video_learning_system.entity.OrderInfo;
import top.keyle.Online_video_learning_system.enums.OrderStatus;
import top.keyle.Online_video_learning_system.enums.wxpay.AliPayTradeState;
import top.keyle.Online_video_learning_system.service.AliPayService;
import top.keyle.Online_video_learning_system.service.OrderInfoService;
import top.keyle.Online_video_learning_system.service.PaymentInfoService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author TMJIE5200
 */
@Service
@Slf4j
public class AliPayServiceImpl implements AliPayService {

    private final ReentrantLock lock = new ReentrantLock();
    @Resource
    private OrderInfoService orderInfoService;
    @Resource
    private AlipayClient alipayClient;
    @Resource
    private Environment config;
    @Resource
    private PaymentInfoService paymentInfoService;

    @Resource
    EduClient eduClient;
    @Transactional(rollbackFor = Exception.class)

    @Override
    public String tradeCreate(String courseId, String memberId) {

        try {
            //生成订单
            log.info("生成订单");
            OrderInfo orderInfo = orderInfoService.createOrderByProductId(courseId, memberId);

            //调用支付宝接口
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            //配置需要的公共请求参数
            //支付完成后，支付宝发起异步通知的地址
            request.setNotifyUrl(config.getProperty("alipay.notify-url"));
            //支付完成后，我们想让页面跳转回课程页面，配置returnUrl
            request.setReturnUrl(config.getProperty("alipay.return-url")+orderInfo.getCourseId());

            //组装当前业务方法的请求参数
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderInfo.getOrderNo());
            BigDecimal total = new BigDecimal(orderInfo.getTotalFee().toString());
            bizContent.put("total_amount", total);
            bizContent.put("pay_time", new Date().toString());
            bizContent.put("subject", orderInfo.getCourseTitle());
            bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

            request.setBizContent(bizContent.toString());

            // 更新课程，购买人数+1
            //执行请求，调用支付宝接口
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);

            if(response.isSuccess()){
                log.info("调用成功，返回结果 ===> " + response.getBody());
                eduClient.updateCourse(courseId);
                return response.getBody();
            } else {
                log.info("调用失败，返回码 ===> " + response.getCode() + ", 返回描述 ===> " + response.getMsg());
                throw new RuntimeException("创建支付交易失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("创建支付交易失败");
        }
    }

    /**
     * 处理订单
     * @param params
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processOrder(Map<String, String> params) {

        log.info("处理订单");

        //获取订单号
        String orderNo = params.get("out_trade_no");

        /*在对业务数据进行状态检查和处理之前，
        要采用数据锁进行并发控制，
        以避免函数重入造成的数据混乱*/
        //尝试获取锁：
        // 成功获取则立即返回true，获取失败则立即返回false。不必一直等待锁的释放
        if(lock.tryLock()) {
            try {

                //处理重复通知
                //接口调用的幂等性：无论接口被调用多少次，以下业务执行一次
                // 根据订单号获取订单状态
                String orderStatus = orderInfoService.getOrderStatus(orderNo);
                if (!OrderStatus.NOTPAY.getType().equals(orderStatus)) {
                    return;
                }

                //更新订单状态
                orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.SUCCESS);

                //记录支付日志
                paymentInfoService.createPaymentInfoForAliPay(params);

            } finally {
                //要主动释放锁
                lock.unlock();
            }
        }

    }

    /**
     * 用户取消订单
     * @param orderNo
     */
    @Override
    public void cancelOrder(String orderNo) {

        //调用支付宝提供的统一收单交易关闭接口
        this.closeOrder(orderNo);

        //更新用户订单状态
        orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.CANCEL);
    }

    /**
     * 查询订单
     * @param orderNo
     * @return 返回订单查询结果，如果返回null则表示支付宝端尚未创建订单
     */
    @Override
    public String queryOrder(String orderNo) {

        try {
            log.info("查单接口调用 ===> {}", orderNo);

            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);
            request.setBizContent(bizContent.toString());

            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                log.info("调用成功，返回结果 ===> " + response.getBody());
                return response.getBody();
            } else {
                log.info("调用失败，返回码 ===> " + response.getCode() + ", 返回描述 ===> " + response.getMsg());
                //throw new RuntimeException("查单接口的调用失败");
                return null;//订单不存在
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("查单接口的调用失败");
        }
    }

    /**
     * 根据订单号调用支付宝查单接口，核实订单状态
     * 如果订单未创建，则更新商户端订单状态
     * 如果订单未支付，则调用关单接口关闭订单，并更新商户端订单状态
     * 如果订单已支付，则更新商户端订单状态，并记录支付日志
     * @param orderNo
     */
    @Override
    public void checkOrderStatus(String orderNo) {

        log.warn("根据订单号核实订单状态 ===> {}", orderNo);

        String result = this.queryOrder(orderNo);

        //订单未创建
        if(result == null){
            log.warn("核实订单未创建 ===> {}", orderNo);
            //更新本地订单状态
            orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.CLOSED);
        }

        //解析查单响应结果
        Gson gson = new Gson();
        HashMap<String, LinkedTreeMap> resultMap = gson.fromJson(result, HashMap.class);
        LinkedTreeMap alipayTradeQueryResponse = resultMap.get("alipay_trade_query_response");

        String tradeStatus = (String)alipayTradeQueryResponse.get("trade_status");
        if(AliPayTradeState.NOTPAY.getType().equals(tradeStatus)){
            log.warn("核实订单未支付 ===> {}", orderNo);

            //如果订单未支付，则调用关单接口关闭订单
            this.closeOrder(orderNo);

            // 并更新商户端订单状态
            orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.CLOSED);
        }

        if(AliPayTradeState.SUCCESS.getType().equals(tradeStatus)){
            log.warn("核实订单已支付 ===> {}", orderNo);

            //如果订单已支付，则更新商户端订单状态
            orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.SUCCESS);

            //并记录支付日志
            paymentInfoService.createPaymentInfoForAliPay(alipayTradeQueryResponse);
        }

    }

    /**
     * 关单接口的调用
     * @param orderNo 订单号
     */
    private void closeOrder(String orderNo) {

        try {
            log.info("关单接口的调用，订单号 ===> {}", orderNo);

            AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);
            request.setBizContent(bizContent.toString());
            AlipayTradeCloseResponse response = alipayClient.execute(request);

            if(response.isSuccess()){
                log.info("调用成功，返回结果 ===> " + response.getBody());
            } else {
                log.info("调用失败，返回码 ===> " + response.getCode() + ", 返回描述 ===> " + response.getMsg());
                //throw new RuntimeException("关单接口的调用失败");
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("关单接口的调用失败");
        }
    }



    /**
     * 申请账单
     * @param billDate
     * @param type
     * @return
     */
    @Override
    public String queryBill(String billDate, String type) {

        try {

            AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
            JSONObject bizContent = new JSONObject();
            bizContent.put("bill_type", type);
            bizContent.put("bill_date", billDate);
            request.setBizContent(bizContent.toString());
            AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);

            if(response.isSuccess()){
                log.info("调用成功，返回结果 ===> " + response.getBody());

                //获取账单下载地址
                Gson gson = new Gson();
                HashMap<String, LinkedTreeMap> resultMap = gson.fromJson(response.getBody(), HashMap.class);
                LinkedTreeMap billDownloadurlResponse = resultMap.get("alipay_data_dataservice_bill_downloadurl_query_response");
                return (String)billDownloadurlResponse.get("bill_download_url");
            } else {
                log.info("调用失败，返回码 ===> " + response.getCode() + ", 返回描述 ===> " + response.getMsg());
                throw new RuntimeException("申请账单失败");
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("申请账单失败");
        }
    }

}
