package top.keyle.online_video_learning_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.keyle.online_video_learning_system.entity.UcenterMember;
import top.keyle.online_video_learning_system.entity.vo.LoginVo;
import top.keyle.online_video_learning_system.entity.vo.RegisterVo;
import top.keyle.online_video_learning_system.mapper.UcenterMemberMapper;
import top.keyle.online_video_learning_system.service.UcenterMemberService;
import top.keyle.universal_tool.GlobalException;
import top.keyle.universal_tool.JwtUtils;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public String login(LoginVo member) {
        // 获取登录手机和密码
        String mobile = member.getMobile();
        String email = member.getEmail();
        String code = member.getCode();
        String password = member.getPassword();
        String jwtToken = "";
        // 手机号码、验证码为空   邮箱不为空   说明是邮箱登录
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
                throw new GlobalException(RespBeanEnum.THE_MOBILE_PHONE_NUMBER_IS_NOT_LINKED);
            }
            // 不为空则验证加密一次密码，去验证
            String dbPassword = mobileMember.getPassword();
            if (dbPassword.equals(password)) {
                // 生成token字符串，使用jwt工具类
                jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
            }else{
                throw new GlobalException(RespBeanEnum.LOGIN_FAILED);
            }
        }

        // 邮箱、验证码为空   手机号不为空   说明是手机登录
        if(!StringUtils.isEmpty(mobile) && StringUtils.isEmpty(email) && StringUtils.isEmpty(code)){
            if(StringUtils.isEmpty(password)){
                throw new GlobalException(RespBeanEnum.THE_PASSWORD_IS_INCORRECT);
            }

            QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
            wrapper.eq("mobile",mobile);
            UcenterMember mobileMember = baseMapper.selectOne(wrapper);
            // 判断查询对象是否为空
            if(mobileMember == null){
                throw new GlobalException(RespBeanEnum.THE_ACCOUNT_IS_NOT_REGISTERED);
            }
            // 不为空则验证加密一次密码，去验证
            String dbPassword = mobileMember.getPassword();
            if (dbPassword.equals(password)) {
                // 生成token字符串，使用jwt工具类
                jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
            }else{
                throw new GlobalException(RespBeanEnum.THE_PASSWORD_IS_INCORRECT);
            }
        }

        // 邮箱、密码为空   手机号不为空   说明是验证码登录
        if(!StringUtils.isEmpty(code) && StringUtils.isEmpty(email) && !StringUtils.isEmpty(mobile) && StringUtils.isEmpty(password)){
            String redisCode = redisTemplate.opsForValue().get(mobile);
            if(redisCode == null){
                throw new GlobalException(RespBeanEnum.CAPTCHA_ERROR);
            }
            if (redisCode.equals(code)) {
                // 比对成功 获取用户信息 返回token
                QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
                wrapper.eq("mobile",mobile);
                UcenterMember mobileMember = baseMapper.selectOne(wrapper);
                jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
            }else{
                throw new GlobalException(RespBeanEnum.CAPTCHA_ERROR);
            }
        }
        return jwtToken;
    }

    @Override
    public RespBean register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        System.out.println(registerVo);
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)  || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)){
            return RespBean.error(RespBeanEnum.THE_DATA_DOES_NOT_EXIST);
        }

        // 判断验证码
        // 获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            return RespBean.error(RespBeanEnum.CAPTCHA_ERROR);
        }

        // 判断手机号码是否重复，表里面存在相同手机号码不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Long count = baseMapper.selectCount(wrapper);
        if(count > 0){
            return RespBean.error(RespBeanEnum.USERS_WITH_THE_SAME_PHONE_NUMBER);
        }

        // 数据添加数据库中
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
    public RespBean loginOut() {
        return null;
    }
}
