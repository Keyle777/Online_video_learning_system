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

@Api(tags = "商品订单管理")
@RestController
@RequestMapping("/orderService/order-info")
public class OrderInfoController {

    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 注释：使用ApiOperation注解来标识该接口的作用
     * 条件分页查询某用户订单列表
     *
     * @param current Integer型，表示当前页码
     * @param limit Integer型，表示每页显示的记录数
     * @param memberId String型，表示会员的ID
     * @return 返回RespBean类型的对象，包含JsonPage<OrderInfo>类型的json数据
     */
    @ApiOperation(value = "条件分页查询某用户订单列表")
    @PostMapping("/pageListOrders/{current}/{limit}/{memberId}")
    public RespBean pageListOrders(
            @PathVariable Integer current,
            @PathVariable Integer limit,
            @PathVariable String memberId) {
        // 调用orderInfoService的pageListOrders方法进行分页查询
        JsonPage<OrderInfo> jsonPage = orderInfoService
                .pageListOrders(current, limit, memberId);
        // 将查询结果包装到RespBean中，并通过RespBean.success方法返回
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
            //支付成功
            return RespBean.success("success","支付成功");
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
                .eq("member_id",memberId)
                .eq("status","支付成功");
        OrderInfo orderInfo = orderInfoService.getOne(wrapper);
        if(orderInfo !=null){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 查询当前用户的订单总额
     * @param member 用户ID
     * @return
     */
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

    /**
     * 删除订单记录
     * @param id 订单ID号码
     * @return
     */
    @DeleteMapping("{id}")
    public RespBean deleteOrder(@PathVariable String id){
        orderInfoService.removeById(id);
        return RespBean.success();
    }

}
