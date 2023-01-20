package top.keyle.online_video_learning_system.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import top.keyle.online_video_learning_system.entry.EduTeacher;
import top.keyle.online_video_learning_system.entry.vo.EduTeacherQuery;
import top.keyle.online_video_learning_system.service.EduTeacherService;
import top.keyle.universal_tool.JsonPage;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

import java.util.HashMap;

/**
 * 讲师前端控制器
 *
 * @author TMJIE5200
 * @date 2022-11-07 22:01:37
 * @week 星期一
 * RestController 此控制器所有方法都返回一个json数据，并交由spring管理此控制器
 * RequestMapping 访问此控制器基础路径
 */
@RestController
@RequestMapping("/eduService/teacher")
@Api(tags = {"讲师管理"})
@CrossOrigin

public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAllTeacher")
    public RespBean findAllTeacher() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("items", eduTeacherService.list());
        return RespBean.success(hashMap);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public RespBean removeById(@ApiParam(name = "id", value = "讲师ID", required = true, example = "1") @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.DELETE_ERROR);
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/getEduTeacherById/{id}")
    @ApiImplicitParam(value = "讲师ID", name = "id", required = true, example = "1")
    public RespBean getEduTeacherById(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        if (!ObjectUtils.isEmpty(eduTeacher)) {
            HashMap<String, EduTeacher> hashMap = new HashMap<>();
            hashMap.put("teacher", eduTeacher);
            return RespBean.success(hashMap);
        }
        return RespBean.error(RespBeanEnum.SELECT_ERROR);
    }

    @ApiOperation(value = "修改讲师信息")
    @PostMapping("/updateEduTeacherByEntry")
    @ApiImplicitParam(value = "讲师实体", name = "eduTeacher", required = true, dataType = "EduTeacher")
    public RespBean updateEduTeacherByEntry(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.UPDATE_ERROR);
    }

    /**
     * todo 使用@RequestBody需要使用post请求，否则对象里面的值取不到
     *
     * @param eduTeacher 讲师
     * @return success/error
     */
    @PostMapping("/insertEduTeacher")
    @ApiOperation(value = "根据讲师实体添加讲师")
    @ApiImplicitParam(value = "讲师实体", name = "eduTeacher", paramType = "body", dataType = "EduTeacher")
    public RespBean insertEduTeacher(@RequestBody(required = false) EduTeacher eduTeacher) {
        if (eduTeacherService.save(eduTeacher)) {
            return RespBean.success(eduTeacher);
        }
        return RespBean.error(RespBeanEnum.ADD_ERROR);
    }

    /**
     * 分页查询教师列表
     * todo 传参采用路径传值 xxx/pageEduTeacher/1/5
     *
     * @return RespBean
     */
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("/pageEduTeacher/{current}/{limit}")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "current", example = "1"),
            @ApiImplicitParam(value = "每页条数", name = "limit", example = "5")
    })
    public RespBean pageListEduTeacher(@PathVariable Integer current, @PathVariable Integer limit) {
        // 分页调用
        JsonPage<EduTeacher> jsonPage = eduTeacherService.getAllOrdersByPage(
                current, limit);
        return RespBean.success(jsonPage);
    }

    @ApiOperation(value = "条件分页查询讲师列表")
    @PostMapping("/pageEduTeacherCondition/{current}/{limit}")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "current", example = "1", required = true),
            @ApiImplicitParam(value = "每页条数", name = "limit", example = "5", required = true),
            @ApiImplicitParam(value = "讲师查询条件对象", name = "eduTeacherQuery", paramType = "body", dataType = "EduTeacherQuery")
    })
    public RespBean pageEduTeacherCondition(
            @PathVariable Integer current,
            @PathVariable Integer limit,
            @RequestBody(required = false) EduTeacherQuery eduTeacherQuery) {
        JsonPage<EduTeacher> jsonPage = eduTeacherService
                .getAllOrdersByPageCondition(current, limit, eduTeacherQuery);
        return RespBean.success(jsonPage);
    }

    @ApiOperation(value = "根据ID修改讲师信息")
    @PostMapping("/modifyLecturerInformation")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "讲师对象", name = "eduTeacher", paramType = "body", dataType = "EduTeacher")
    })
    public RespBean modifyLecturerInformation(
            @RequestBody(required = false) EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateSelective(eduTeacher);
        if (flag) {
            return RespBean.success("eduTeacher", eduTeacherService.getById(eduTeacher.getId()));
        }
        return RespBean.error(RespBeanEnum.UPDATE_ERROR);
    }

    @ApiOperation(value = "根据Id批量删除讲师")
    @GetMapping("/deleteLecturersInBatchesById")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "所勾选的讲师ID", name = "eduTeachersId")
    })
    public RespBean deleteLecturersInBatchesById(
            String eduTeachersId
    ) {

        if (eduTeachersId.isEmpty()) {
            return RespBean.error();
        }
        String[] Ids = eduTeachersId.split(",");
        for (String id : Ids) {
            eduTeacherService.removeById(id);
        }
        return RespBean.success();
    }
}
