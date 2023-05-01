package top.keyle.Online_video_learning_system.controller;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.Online_video_learning_system.entry.CrmBanner;
import top.keyle.Online_video_learning_system.service.CrmBannerService;
import top.keyle.universal_tool.RedisCache;
import top.keyle.universal_tool.RespBean;

import java.util.List;

/**
 * @Author TMJIE5200
 * @Date 2023/2/07
 */
@Api(tags = "前台Banner")
@RestController
@RequestMapping("/cmsService/bannerFront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @Autowired
    RedisCache redisCache;
    /**
     * 查询所有的banner
     * @return
     */

    @GetMapping("getAllBanner")
    public RespBean getAllBanner(){
        List<Object> bannaerList = redisCache.getCacheList("bannaerList");
        if(bannaerList !=null && bannaerList.size() > 0){
            return RespBean.success("list",bannaerList);
        }
        List<CrmBanner> list = bannerService.selectAllBanner();
        redisCache.setCacheList("bannaerList",list);
        return RespBean.success("list",list);
    }
}
