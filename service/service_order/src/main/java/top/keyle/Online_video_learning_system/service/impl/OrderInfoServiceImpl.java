package top.keyle.Online_video_learning_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.Online_video_learning_system.client.EduClient;
import top.keyle.Online_video_learning_system.client.MemberClient;
import top.keyle.Online_video_learning_system.entity.EduCourse;
import top.keyle.Online_video_learning_system.entity.EduTeacher;
import top.keyle.Online_video_learning_system.entity.OrderInfo;
import top.keyle.Online_video_learning_system.entity.UcenterMember;
import top.keyle.Online_video_learning_system.enums.OrderStatus;
import top.keyle.Online_video_learning_system.mapper.OrderInfoMapper;
import top.keyle.Online_video_learning_system.service.OrderInfoService;
import top.keyle.Online_video_learning_system.util.OrderNoUtils;
import top.keyle.universal_tool.JsonPage;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
    @Autowired
    EduClient eduClient;

    @Autowired
    MemberClient memberClient;

    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Override
    public OrderInfo createOrderByProductId(String courseId, String memberId) {
        OrderInfo orderInfo = this.getNoPayOrderByProductId(courseId, memberId);
        if( orderInfo != null){
            return orderInfo;
        }
        //获取一些信息填入订单表中
        EduCourse courseInfo = orderInfoMapper.selectCourseByCourseId(courseId);
        EduTeacher eduTeacher = eduClient.getEduTeacherByIdFront(courseInfo.getTeacherId());
        UcenterMember ucenterMember = memberClient.getInfo(memberId);
        //生成订单
        orderInfo = new OrderInfo();
        orderInfo.setCourseTitle(courseInfo.getTitle());
        orderInfo.setOrderNo(OrderNoUtils.getOrderNo());
        orderInfo.setTotalFee(courseInfo.getPrice());
        orderInfo.setNickname(ucenterMember.getNickname());
        orderInfo.setCourseId(courseInfo.getId());
        orderInfo.setCourseCover(courseInfo.getCover());
        orderInfo.setTeacherName(eduTeacher.getName());
        orderInfo.setMemberId(memberId);
        orderInfo.setMobile(ucenterMember.getMobile());
        orderInfo.setStatus(OrderStatus.NOTPAY.getType());
        baseMapper.insert(orderInfo);
        return orderInfo;
    }

    /**
     * 查询订单列表，并倒序查询
     * @return
     */
    @Override
    public List<OrderInfo> listOrderByCreateTimeDesc() {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>().orderByDesc("gmt_create");
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据订单号更新订单状态
     * @param orderNo
     * @param orderStatus
     */
    @Override
    public void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus) {

        log.info("更新订单状态 ===> {}", orderStatus.getType());

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStatus(orderStatus.getType());

        baseMapper.update(orderInfo, queryWrapper);
    }

    /**
     * 根据订单号获取订单状态
     * @param orderNo
     * @return
     */
    @Override
    public String getOrderStatus(String orderNo) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        OrderInfo orderInfo = baseMapper.selectOne(queryWrapper);
        if(orderInfo == null){
            return null;
        }
        return orderInfo.getStatus();
    }

    /**
     * 查询创建超过minutes分钟并且未支付的订单
     * @param minutes
     * @return
     */
    @Override
    public List<OrderInfo> getNoPayOrderByDuration(int minutes) {

        Instant instant = Instant.now().minus(Duration.ofMinutes(minutes));

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", OrderStatus.NOTPAY.getType());
        queryWrapper.le("gmt_create", instant);
        queryWrapper.eq("pay_type", 2);

        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据订单号获取订单
     * @param orderNo
     * @return
     */
    @Override
    public OrderInfo getOrderByOrderNo(String orderNo) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        return baseMapper.selectOne(queryWrapper);
    }


    /**
     * 根据用户ID和课程ID查询订单中是否存在订单或未支付的订单。
     * 防止重复创建订单对象
     * @param courseId 课程ID
     * @param memberId 用户ID
     * @return 一条订单信息
     */
    private OrderInfo getNoPayOrderByProductId(String courseId,String memberId) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("status", OrderStatus.NOTPAY.getType());
        queryWrapper.eq("member_id", memberId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public JsonPage<OrderInfo> pageListOrders(Integer page, Integer pageSize, String memberId) {
        PageHelper.startPage(page, pageSize);
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId);
        List<OrderInfo> eduOrderInfoList = orderInfoMapper.selectList(wrapper);
        return JsonPage.restPage(new PageInfo<>(eduOrderInfoList));
    }

    @Override
    public Integer selectCurrentWeekOrder(String memberId) {
        return orderInfoMapper.selectCurrentMonthOrder(memberId);
    }

    @Override
    public Integer selectCurrentDayOrder(String memberId) {
        return orderInfoMapper.selectCurrentDayOrder(memberId);
    }

    @Override
    public Integer selectCurrentAllOrder(String memberId) {
        return orderInfoMapper.selectCurrentAllOrder(memberId);
    }
}
