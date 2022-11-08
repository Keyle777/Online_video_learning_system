package top.keyle.online_video_learning_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.online_video_learning_system.pojo.EduTeacher;
import top.keyle.online_video_learning_system.service.EduTeacherService;

import java.util.List;

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
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("/findAllTeacher")
    public List<EduTeacher> findAllTeacher() {
        return eduTeacherService.list();
    }

    @DeleteMapping("{id}")
    public Boolean removeById(@PathVariable String id) {
        return eduTeacherService.removeById(id);
    }

    @GetMapping("/insertEduTeacher")
    public Boolean insertEduTeacher(@RequestBody EduTeacher eduTeacher){
        return eduTeacherService.insertSelective(eduTeacher) > 0;
    }
}
