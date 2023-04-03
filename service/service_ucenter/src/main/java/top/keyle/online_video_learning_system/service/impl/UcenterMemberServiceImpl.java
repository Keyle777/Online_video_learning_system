package top.keyle.online_video_learning_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.keyle.online_video_learning_system.entity.UcenterMember;
import top.keyle.online_video_learning_system.entity.vo.LoginVo;
import top.keyle.online_video_learning_system.entity.vo.RegisterVo;
import top.keyle.online_video_learning_system.entity.vo.courseVO.courseVO;
import top.keyle.online_video_learning_system.mapper.UcenterMemberMapper;
import top.keyle.online_video_learning_system.service.UcenterMemberService;
import top.keyle.universal_tool.*;

import java.util.List;

@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public int updateLoginCountById(String id) {
        return baseMapper.updateLoginCountById(id);
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户登录
     *
     * @param member 登录信息
     * @return 返回生成的JWT Token
     */
    @Override
    public String login(LoginVo member) {
        // 获取登录手机和密码
        String mobile = member.getMobile();
        String email = member.getEmail();
        String code = member.getCode();
        String password = member.getPassword();
        String jwtToken = "";
        // 手机号码、验证码为空，邮箱不为空，说明是邮箱登录
        if(StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(email) && StringUtils.isEmpty(code)){
            if(StringUtils.isEmpty(password)){
                throw new GlobalException(RespBeanEnum.LOGIN_FAILED);
            }
            // 判断邮箱是否正确
            QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
            wrapper.eq("email",email);
            UcenterMember mobileMember = baseMapper.selectOne(wrapper);
            // 判断查询对象是否为空
            if(mobileMember == null){
                // 如果查询对象为空，说明该邮箱没有绑定账号，抛出手机号未绑定的全局异常
                throw new GlobalException(RespBeanEnum.THE_MOBILE_PHONE_NUMBER_IS_NOT_LINKED);
            }
            // 不为空则验证加密一次密码，去验证
            String dbPassword = mobileMember.getPassword();
            if (dbPassword.equals(password)) {
                // 生成token字符串，使用jwt工具类
                jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
                this.updateLoginCountById(mobileMember.getId());
            }else{
                // 如果密码不匹配，抛出登录失败的全局异常
                throw new GlobalException(RespBeanEnum.LOGIN_FAILED);
            }
        }

        // 邮箱、验证码为空，手机号不为空，说明是手机登录
        if(!StringUtils.isEmpty(mobile) && StringUtils.isEmpty(email) && StringUtils.isEmpty(code)){
            if(StringUtils.isEmpty(password)){
                // 如果密码为空，抛出密码错误的全局异常
                throw new GlobalException(RespBeanEnum.THE_PASSWORD_IS_INCORRECT);
            }

            QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
            wrapper.eq("mobile",mobile);
            UcenterMember mobileMember = baseMapper.selectOne(wrapper);
            // 判断查询对象是否为空
            if(mobileMember == null){
                // 如果查询对象为空，说明该手机号未注册，抛出账号未注册的全局异常
                throw new GlobalException(RespBeanEnum.THE_ACCOUNT_IS_NOT_REGISTERED);
            }
            // 不为空则验证加密一次密码，去验证
            String dbPassword = mobileMember.getPassword();
            if (dbPassword.equals(password)) {
                // 生成token字符串，使用jwt工具类
                jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
                this.updateLoginCountById(mobileMember.getId());
            }else{
                // 如果密码不匹配，抛出密码错误的全局异常
                throw new GlobalException(RespBeanEnum.THE_PASSWORD_IS_INCORRECT);
            }
        }

        // 邮箱、密码为空，手机号不为空，说明是验证码登录
        if(!StringUtils.isEmpty(code) && StringUtils.isEmpty(email) && !StringUtils.isEmpty(mobile) && StringUtils.isEmpty(password)){
            String redisCode = redisTemplate.opsForValue().get(mobile);
            if(redisCode == null){
                // 如果redis中没有该手机号对应的验证码，抛出验证码错误的全局异常
                throw new GlobalException(RespBeanEnum.CAPTCHA_ERROR);
            }
            if (redisCode.equals(code)) {
                // 比对成功 获取用户信息 返回token
                QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
                wrapper.eq("mobile",mobile);
                UcenterMember mobileMember = baseMapper.selectOne(wrapper);
                if(mobileMember == null){
                    return null;
                }
                jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
                this.updateLoginCountById(mobileMember.getId());
            }else{
                throw new GlobalException(RespBeanEnum.CAPTCHA_ERROR);
            }
        }
        return jwtToken;
    }

    /**
     * 注册功能
     * @param registerVo 注册信息
     * @return 注册结果
     */
    @Override
    public RespBean register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        System.out.println(registerVo);
        // 判断注册信息是否完整
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)  || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)){
            return RespBean.error(RespBeanEnum.THE_DATA_DOES_NOT_EXIST);
        }

        // 判断验证码是否正确
        // 获取redis中保存的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            return RespBean.error(RespBeanEnum.CAPTCHA_ERROR);
        }

        // 判断手机号码是否重复，存在相同手机号码不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Long count = baseMapper.selectCount(wrapper);
        if(count > 0){
            return RespBean.error(RespBeanEnum.USERS_WITH_THE_SAME_PHONE_NUMBER);
        }
        // 将注册信息添加到数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(password);
        member.setAvatar("https://keyle777.oss-cn-nanjing.aliyuncs.com/image/202211091945728.jpg");
        baseMapper.insert(member);
        return RespBean.success();
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }

    @Override
    public Integer countVideoPlay(String day) {
        return baseMapper.countVideoPlay(day);
    }

    @Override
    public Integer countCourseInsert(String day) {
        return baseMapper.countCourseInsert(day);
    }

    @Override
    public Integer countLogin(String day) {
        return baseMapper.countLogin(day);
    }

    @Override
    public RespBean loginOut() {
        return null;
    }


    @Override
    public JsonPage<courseVO> selectCourseTostudy(Integer page, Integer pageSize, String id) {
        PageHelper.startPage(page, pageSize);
        List<courseVO> eduCourseCollectList = baseMapper.selectCourseTostudy(id);
        return JsonPage.restPage(new PageInfo<>(eduCourseCollectList));
    }
}
