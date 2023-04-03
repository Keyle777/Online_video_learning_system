package top.keyle.Online_video_learning_system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.Online_video_learning_system.entry.CrmBanner;
import top.keyle.Online_video_learning_system.entry.vo.CrmBannerVo;
import top.keyle.Online_video_learning_system.service.CrmBannerService;
import top.keyle.universal_tool.JsonPage;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;


@Api(tags = "后台Banner管理")
@RestController
@RequestMapping("/cmsService/crmBanner")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 分页查询
     * @param page 页码
     * @param limit 每页显示数据亮
     * @return
     */
    @PostMapping("pageBanner/{page}/{limit}")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "page", example = "1", required = true),
            @ApiImplicitParam(value = "每页条数", name = "limit", example = "5", required = true),
            @ApiImplicitParam(value = "讲师查询条件对象", name = "query", paramType = "body", dataType = "CrmBannerVo")
    })
    public RespBean pageBanner(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            @RequestBody(required = false) CrmBannerVo query){
        // 分页调用
        JsonPage<CrmBanner> jsonPage = bannerService.paginateToGetListOfBanners(page, limit,query);
        System.out.println(jsonPage);
        return RespBean.success(jsonPage);
    }

    /**
     * 添加Banner的请求处理函数
     *
     * @param crmBanner 待添加的Banner对象
     * @return 表示添加操作结果的RespBean对象
     */
    @PostMapping("addBanner")
    public RespBean addBanner(@RequestBody(required = false) CrmBanner crmBanner){
        //  调用bannerService的save方法将CrmBanner对象添加的数据库中
        boolean save = bannerService.save(crmBanner);
        if (save) {
            // 添加操作成功，返回成功状态的RespBean对象
            return RespBean.success();
        }else{
            // 添加操作失败，返回包含错误信息的RespBean对象
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

    @GetMapping("getMaxSort")
    public RespBean getMaxSort(){
        return RespBean.success("maxSort",bannerService.getMaxSort());
    }

}

