package top.keyle.online_video_learning_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.online_video_learning_system.entity.EduCourseCollect;
import top.keyle.online_video_learning_system.entity.vo.collectionVO.CollectDetail;
import top.keyle.online_video_learning_system.service.EduCourseCollectService;
import top.keyle.online_video_learning_system.service.UcenterMemberService;
import top.keyle.universal_tool.JsonPage;
import top.keyle.universal_tool.RespBean;

import java.util.HashMap;
import java.util.List;

/**
 * @author TMJIE5200
 * @date 2023-02-11 18:37:48
 * @week 星期六
 */
@Api(tags = {"收藏管理"})
@RestController
@RequestMapping("/eduTraineeCenter/courseCollect")
@CrossOrigin
public class EduCourseCollectController {
    @Autowired
    EduCourseCollectService eduCourseCollectService;

    @Autowired
    UcenterMemberService memberService;

    @GetMapping("/selectDayCollectionCount")
    public RespBean selectDayCollectionCoutn(String id){
        // 先判断该用户是否存在，存在再去查后面的内容，不存在也别返回空内容，nuxt前端会显示underfind
        // 最好的方法是 返回一个你预想到的初始值，比如这里的每天收藏数，你查不到这个用户，那么结果就返回0，不要返回null！！！
        HashMap<String, Object> hashMap = new HashMap<>();
        if (memberService.getById(id) != null) {
            int count = eduCourseCollectService.selectDayCollectionCount(id);
            hashMap.put("CollectionCoutn",count);
            return RespBean.success(hashMap);
        }
        hashMap.put("CollectionCount",0);
        return RespBean.success(hashMap);
    }

    @GetMapping("/SelectNumberOfCollectionsInAWeek")
    public RespBean SelectNumberOfCollectionsInAWeek(String id){
        HashMap<String, Object> hashMap = new HashMap<>();
        if (memberService.getById(id) != null) {
            int count = eduCourseCollectService.SelectNumberOfCollectionsInAWeek(id);
            hashMap.put("CollectionCount",count);
            return RespBean.success(hashMap);
        }
        hashMap.put("CollectionCount",0);
        return RespBean.success(hashMap);
    }


    @GetMapping("/SelectFreeVsPaid")
    public RespBean SelectFreeVsPaid(String id){
        HashMap<String, Object> hashMap = eduCourseCollectService.SelectFreeVsPaid(id);
        if (!hashMap.isEmpty()){
            return RespBean.success(hashMap);
        }
        hashMap.put("gratis",0);
        hashMap.put("toll",0);
        return RespBean.success(hashMap);
    }

    @GetMapping("/selectCollectCount")
    public RespBean selectCollectCount(String id){
        HashMap<String, Object> hashMap = new HashMap<>();
        if (memberService.getById(id) != null) {
            QueryWrapper<EduCourseCollect> wrapper = new QueryWrapper<>();
            wrapper.eq("member_id",id);
            long count = eduCourseCollectService.count(wrapper);
            hashMap.put("CollectionCount",count);
        }else{
            hashMap.put("CollectionCount",0);
        }
        return RespBean.success(hashMap);
    }

    @GetMapping("/selectCollectList")
    public RespBean selectCollectList(String id){
        List<CollectDetail> collectDetailList = eduCourseCollectService.selectCollectList(id);
        HashMap<Object, Object> map = new HashMap<>();
        if (collectDetailList != null) {
            map.put("collectDetailList",collectDetailList);
            return RespBean.success(map);
        }else{
            return RespBean.error();
        }
    }

    @ApiOperation(value = "分页查询收藏列表")
    @GetMapping("/pageCollect/{current}/{limit}/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "current", example = "1"),
            @ApiImplicitParam(value = "每页条数", name = "limit", example = "5"),
            @ApiImplicitParam(value = "用户ID", name = "id", example = "1623698548130955265")
    })
    public RespBean pageCollectList(@PathVariable Integer current, @PathVariable Integer limit,@PathVariable String id) {
        // 分页调用
        JsonPage<CollectDetail> jsonPage = eduCourseCollectService.paginateToGetAListOfInstructors(
                current, limit , id);
        return RespBean.success(jsonPage);
    }


    @DeleteMapping("{id}")
    public RespBean deleteCollect(@PathVariable String id){
        if(StringUtils.isEmpty(id)){
            return RespBean.error();
        }
        boolean removeById = eduCourseCollectService.removeById(id);
        if (removeById){
            return RespBean.success();
        }
        return RespBean.error();
    }
}
