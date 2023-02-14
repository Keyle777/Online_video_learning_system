package top.keyle.Online_video_learning_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.keyle.Online_video_learning_system.entity.OrderInfo;
import top.keyle.Online_video_learning_system.entity.orderVo.OrderVo;
import top.keyle.Online_video_learning_system.enums.OrderStatus;
import top.keyle.Online_video_learning_system.service.OrderInfoService;
import top.keyle.Online_video_learning_system.vo.R;
import top.keyle.universal_tool.JsonPage;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@CrossOrigin //开放前端的跨域访问
@Api(tags = "商品订单管理")
@RestController
@RequestMapping("/orderService/order-info")
public class OrderInfoController {

    @Resource
    private OrderInfoService orderInfoService;

    @ApiOperation(value = "条件分页查询某用户订单列表")
    @PostMapping("/pageListOrders/{current}/{limit}/{memberId}")
    public RespBean pageListOrders(
            @PathVariable Integer current,
            @PathVariable Integer limit,
            @PathVariable String memberId) {
        JsonPage<OrderInfo> jsonPage = orderInfoService
                .pageListOrders(current, limit, memberId);
        return RespBean.success(jsonPage);
    }

    @ApiOperation("订单列表")
    @GetMapping("/list")
    public R list(){
        List<OrderInfo> list = orderInfoService.listOrderByCreateTimeDesc();
        return R.ok().data("list", list);
    }

    @ApiOperation(value = "条件分页查询订单列表")
    @PostMapping("/pageAllListOrders/{current}/{limit}")
    public RespBean pageAllListOrders(
            @PathVariable Integer current,
            @PathVariable Integer limit,
            @RequestBody OrderVo orderVo
            ) {
        JsonPage<OrderInfo> jsonPage = orderInfoService
                .pageAllListOrders(current, limit , orderVo);
        return RespBean.success(jsonPage);
    }

    /**
     * 查询本地订单状态
     * @param orderNo
     * @return
     */
    @ApiOperation("查询本地订单状态")
    @GetMapping("/query-order-status/{orderNo}")
    public RespBean queryOrderStatus(@PathVariable String orderNo){

        String orderStatus = orderInfoService.getOrderStatus(orderNo);
        if(OrderStatus.SUCCESS.getType().equals(orderStatus)){
            return RespBean.success("success","支付成功"); //支付成功
        }
        return RespBean.success(RespBeanEnum.PAY);
    }

    @ApiOperation("查询本地订单状态")
    @GetMapping("/isBuy/{memberId}/{courseId}")
    public Boolean isBuy(@PathVariable String memberId,@PathVariable String courseId){
        if(memberId ==null || courseId ==null){
            return false;
        }
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId)
                .eq("member_id",memberId);
        OrderInfo orderInfo = orderInfoService.getOne(wrapper);
        if(orderInfo !=null){
            return true;
        }else {
            return false;
        }
    }

    @GetMapping("selectCurrentOrderTotal/{member}")
    public RespBean selectCurrentOrderTotal(@PathVariable String member){
        Integer dayOrder = orderInfoService.selectCurrentDayOrder(member);
        Integer weekOrder = orderInfoService.selectCurrentWeekOrder(member);
        Integer allOrder = orderInfoService.selectCurrentAllOrder(member);
        if(dayOrder == null){
            dayOrder = 0;
        }
        if(weekOrder == null){
            dayOrder = 0;
        }
        if(allOrder == null){
            dayOrder = 0;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("dayOrder",dayOrder);
        hashMap.put("monthOrder",weekOrder);
        hashMap.put("allOrder",allOrder);
        return RespBean.success(hashMap);
    }

    @DeleteMapping("{id}")
    public RespBean deleteOrder(@PathVariable String id){
        orderInfoService.removeById(id);
        return RespBean.success();
    }

}
