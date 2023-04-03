package top.keyle.online_video_learning_system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.online_video_learning_system.entity.UcenterMember;
import top.keyle.online_video_learning_system.entity.vo.LoginVo;
import top.keyle.online_video_learning_system.entity.vo.RegisterVo;
import top.keyle.online_video_learning_system.entity.vo.StatisticsDaily;
import top.keyle.online_video_learning_system.entity.vo.courseVO.courseVO;
import top.keyle.online_video_learning_system.service.EduCourseCollectService;
import top.keyle.online_video_learning_system.service.UcenterMemberService;
import top.keyle.universal_tool.JsonPage;
import top.keyle.universal_tool.JwtUtils;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"前端用户管理"})
@RestController
@RequestMapping("/eduTraineeCenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @Autowired
    EduCourseCollectService eduCourseCollectService;

    /**
     * 注释：使用@PostMapping注解标识该方法为处理POST请求的方法
     * 实现用户登录功能，接受请求体参数LoginVo对象
     *
     * @param member LoginVo类型的对象，封装了用户的手机号和密码
     * @return 返回RespBean类型的对象，包含token值，使用jwt生成
     */
    @PostMapping("/login")
    public RespBean loginUser(@RequestBody LoginVo member){
        //  调用memberService的login方法实现用户登录，返回token值
        String token = memberService.login(member);
        //  如果token为null，说明登录失败，返回错误信息
        if(token == null){
            return RespBean.error(RespBeanEnum.THE_ACCOUNT_IS_NOT_REGISTERED);
        }
        //  如果登录成功，将token封装到RespBean中并返回
        return RespBean.success("token",token);
    }
    @GetMapping("/statistics/{day}")
    public RespBean statistics(@PathVariable String day){
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setLoginNum(memberService.countLogin(day));
        statisticsDaily.setCourseNum(memberService.countCourseInsert(day));
        statisticsDaily.setRegisterNum(memberService.countRegisterDay(day));
        statisticsDaily.setVideoViewNum(memberService.countVideoPlay(day));
        return RespBean.success("statistics",statisticsDaily);
    }

    @PostMapping("/register")
    public RespBean registerUser(@RequestBody RegisterVo registerVo){
            return memberService.register(registerVo);
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
    @GetMapping("/getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        return ucenterMember;
    }

    @GetMapping("/getInfoUc2")
    public RespBean getInfo2( String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        if (ucenterMember !=null){
            return RespBean.success("memeber",ucenterMember);
        }
        return RespBean.error();
    }
    /**
     * 查询某一天注册人数
     * @param day 日期
     * @return 该日期下的注册人数
     */
    @GetMapping("/countRegister/{day}")
    public Integer countRegister(@PathVariable String day){
        return memberService.countRegisterDay(day);
    }

    /**
     * 修改用户信息
     * @param ucenterMember 用户对象
     * @return 修改结果的响应实体
     */
    @PostMapping("updateUserInformation")
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户对象", name = "ucenterMember", paramType = "body", dataType = "UcenterMember")
    })
    public RespBean modifyLecturerInformation(
            @RequestBody(required = false) UcenterMember ucenterMember) {
        if(ucenterMember== null){
            // 如果用户对象为空，返回修改失败的响应实体
            return RespBean.error(RespBeanEnum.UPDATE_ERROR);
        }
        // 调用用户服务的更新方法，得到更新结果
        boolean flag = memberService.updateById(ucenterMember);
        if (flag) {
            // 如果更新成功，返回成功的响应实体
            return RespBean.success();
        }
        // 如果更新失败，返回修改失败的响应实体
        return RespBean.error(RespBeanEnum.UPDATE_ERROR);
    }

    /**
     分页查询我的学习数据列表
     @param current 当前页码
     @param limit 每页条数
     @param id 用户ID
     @param isFree 是否免费
     @return 响应结果，包含分页查询结果的JsonPage对象
     */
    @ApiOperation(value = "分页查询我的学习数据列表")
    @GetMapping("/selectToStudy/{current}/{limit}/{id}/{isFree}")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "current", example = "1"),
            @ApiImplicitParam(value = "每页条数", name = "limit", example = "5"),
            @ApiImplicitParam(value = "用户ID", name = "id", example = "1623698548130955265")
    })
    public RespBean pageCourseList(
            @PathVariable Integer current,
            @PathVariable Integer limit,
            @PathVariable String id,
            @PathVariable String isFree) {
        // 判断是否是免费课程，如果是免费课程则放入我的收藏里面
        if ("free".equals(isFree)) {
            JsonPage<courseVO> jsonPage1 = eduCourseCollectService.selectCourseCollectionTostudy(
                    current, limit, id);
            return RespBean.success(jsonPage1);
        }
        //  不是免费的课程则去查询用户购买的付费的课程
        JsonPage<courseVO> jsonPage2 = memberService.selectCourseTostudy(
                current, limit , id);
        return RespBean.success(jsonPage2);
    }
}

