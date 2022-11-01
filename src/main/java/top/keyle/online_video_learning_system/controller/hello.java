package top.keyle.online_video_learning_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author TMJIE5200
 * @date 2022-11-01 23:34:04
 * @week 星期二
 */
@Controller
public class hello {
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "你好";
    }
}
