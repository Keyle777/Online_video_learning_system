package top.keyle.msm_service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.msm_service.service.MsmService;
import top.keyle.universal_tool.RandomUtil;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author TMJIE5200
 * @date 2023-01-10 14:42:53
 * @week 星期二
 */
@RestController
@RequestMapping("/msmService/msm")
public class MsmApiController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     *
     * @param phone 电话号码
     * @return
     */
    @GetMapping(value = "/send/{phone}")
    public RespBean code(@PathVariable String phone) {
        // 通过电话号码从redis中获取对应的code
        String code = redisTemplate.opsForValue().get(phone);
        if(!ObjectUtils.isEmpty(code)) {
            // 如果发现存在code，则表明发送成功。
            return RespBean.success();
        }
        // 生成随机数字作为code
        code = RandomUtil.getSixBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        // 通过阿里云的短信服务将code发送到手机上，返回发送结果成功或是失败
        boolean isSend = msmService.send(phone, "SMS_154950909", param);
        if(isSend) {
            // 设置 电话、验证码、过期时间、时间单位
            redisTemplate.opsForValue().set(phone, code,1, TimeUnit.MINUTES);
            // 表明发送成功
            return RespBean.success();
        } else {
            // 发送失败
            return RespBean.error(RespBeanEnum.ERROR_FAILED_TO_SEND_MSM);
        }
    }
}
