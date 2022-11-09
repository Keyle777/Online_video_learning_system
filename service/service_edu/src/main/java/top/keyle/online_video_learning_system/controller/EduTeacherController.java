package top.keyle.online_video_learning_system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.online_video_learning_system.pojo.EduTeacher;
import top.keyle.online_video_learning_system.service.EduTeacherService;
import top.keyle.universal_tool.RespBean;

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
@RequestMapping("/eduservice/teacher")
@Api(tags = {"讲师管理"})
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAllTeacher")
    public RespBean findAllTeacher() {
        return RespBean.success("items",eduTeacherService.list());
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public RespBean removeById(@ApiParam(name = "id", value = "讲师ID", required = true, example = "1") @PathVariable String id) {
        return RespBean.success();
    }

    @ApiOperation(value = "根据讲师实体添加讲师")
    @GetMapping("/insertEduTeacher")
    public RespBean insertEduTeacher(@ApiParam(name = "eduTeacher",value = "讲师实体",required = true) @RequestBody EduTeacher eduTeacher) {
        return RespBean.success();
    }
}
