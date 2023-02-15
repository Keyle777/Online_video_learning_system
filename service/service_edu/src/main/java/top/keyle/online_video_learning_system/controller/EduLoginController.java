package top.keyle.online_video_learning_system.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.universal_tool.RespBean;

import java.util.HashMap;

/**
 * @author TMJIE5200
 * @date 2022-11-12 17:16:51
 * @week 星期六
 */
@RestController
@RequestMapping("/eduService/user")
@Api(tags = {"讲师后台登录"})
public class EduLoginController {
    @PostMapping("/login")
    public RespBean login(){
        System.out.println("登录成功");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token","admin");
        return RespBean.success(hashMap);
    }

    @GetMapping("/info")
    public RespBean info(){
        System.out.println("显示登录用户信息");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name","admin");
        hashMap.put("avatar","https://keyle777.oss-cn-nanjing.aliyuncs.com/image/202211122116719.png");
        return RespBean.success(hashMap);
    }
    @PostMapping("/logout")
    public RespBean logout(){
        System.out.println("成功退出");
        return RespBean.success();
    }

}
