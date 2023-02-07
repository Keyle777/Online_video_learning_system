package top.keyle.Online_video_learning_system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.Online_video_learning_system.entry.CrmBanner;
import top.keyle.Online_video_learning_system.service.CrmBannerService;
import top.keyle.universal_tool.JsonPage;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;


@Api(tags = "后台Banner管理")
@RestController
@RequestMapping("/eduCms/crmBanner")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 分页查询
     * @param page 页码
     * @param limit 每页显示数据亮
     * @return
     */
    @GetMapping("pageBanner/{page}/{limit}")
    public RespBean pageBanner(@PathVariable Integer page, @PathVariable Integer limit){
        // 分页调用
        JsonPage<CrmBanner> jsonPage = bannerService.paginateToGetListOfBanners(page, limit);
        return RespBean.success(jsonPage);
    }

    /**
     * 添加banner
     * @return
     */
    @PostMapping("addBanner")
    public RespBean addBanner(@RequestBody CrmBanner crmBanner){
        boolean save = bannerService.save(crmBanner);
        if (save) {
            return RespBean.success();
        }else{
            return RespBean.error(RespBeanEnum.ADD_ERROR);
        }
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("getBanner/{id}")
    public RespBean getBanner(@PathVariable String id){
        CrmBanner banner = bannerService.getById(id);
        return RespBean.success("item",banner);
    }

    @ApiOperation(value = "修改Banner")
    @PostMapping("update")
    public RespBean updateById(@RequestBody CrmBanner banner) {
        boolean b = bannerService.updateById(banner);
        if (b) {
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.UPDATE_ERROR);
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("deleteBannerById/{id}")
    public RespBean remove(@PathVariable String id) {
        boolean b = bannerService.removeById(id);
        if (b) {
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.DELETE_ERROR);
    }

}

