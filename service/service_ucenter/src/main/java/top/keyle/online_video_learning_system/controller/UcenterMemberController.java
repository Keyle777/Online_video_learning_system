package top.keyle.online_video_learning_system.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.online_video_learning_system.entity.UcenterMember;
import top.keyle.online_video_learning_system.entity.vo.LoginVo;
import top.keyle.online_video_learning_system.entity.vo.RegisterVo;
import top.keyle.online_video_learning_system.service.UcenterMemberService;
import top.keyle.universal_tool.JwtUtils;
import top.keyle.universal_tool.RespBean;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/eduTraineeCenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 登录
     * @param member 用户表单
     * @return token
     */
    @PostMapping("/login")
    public RespBean loginUser(@RequestBody LoginVo member){
        System.out.println(member);
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = memberService.login(member);
        return RespBean.success("token",token);
    }

    @PostMapping("register")
    public RespBean registerUser(@RequestBody RegisterVo registerVo){
        try {
            memberService.register(registerVo);
            return RespBean.success();
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error();
        }
    }

    /**
     * 根据token获取用户信息
     * @param request
     * @return
     */
    @GetMapping("getMemberInfo")
     public RespBean getMemberInfo(HttpServletRequest request){

        // 调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return RespBean.success("userInfo",member);
    }

    /**
     * 评论模块根据用户id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        UcenterMember memeber = new UcenterMember();
        BeanUtils.copyProperties(ucenterMember,memeber);
        return memeber;
    }

    /**
     * 订单模块根据用户id获取用户信息
     * @param id
     * @return
     */
    /*@GetMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember,ucenterMemberOrder);
        return ucenterMemberOrder;
    }*/

    /**
     * 查询某一天注册人数
     * @param day 日期
     * @return 该日期下的注册人数
     */
    @GetMapping("/countRegister/{day}")
    public RespBean countRegister(@PathVariable String day){
        Integer count = memberService.countRegisterDay(day);
        return RespBean.success("countRegister",count);
    }
}

