package top.keyle.Online_video_learning_system.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.Online_video_learning_system.service.IndexService;
import top.keyle.universal_tool.RespBean;

import java.util.List;
import java.util.Map;

@Api(tags = "管理端首页")
@RestController
@RequestMapping("/admin/acl/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public RespBean info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return RespBean.success(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("menu")
    public RespBean getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        List<JSONObject> permissionList = indexService.getMenu(username);
        System.out.println("==============================");
        System.out.println(permissionList);
        return RespBean.success("permissionList", permissionList);
    }

    @PostMapping("logout")
    public RespBean logout(){
        return RespBean.success();
    }

}
