package top.keyle.online_video_learning_system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.keyle.online_video_learning_system.entry.vo.subject.OneSubject;
import top.keyle.online_video_learning_system.service.EduSubjectService;
import top.keyle.universal_tool.RespBean;

import java.util.HashMap;
import java.util.List;

/**
 * @author TMJIE5200
 * @date 2023-01-20 13:57:05
 * @week 星期五
 */
@RestController
@RequestMapping("/eduService/edu_subject")
@Api(tags = {"课程分类"})
@CrossOrigin
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;
    @ApiOperation(value = "课程科目列表")
    @GetMapping("")
    public RespBean nestedList(){
        List<OneSubject> oneSubjectList = eduSubjectService.nestedList();
        HashMap<String, List<OneSubject>> hashMap = new HashMap<>();
        hashMap.put("items", oneSubjectList);
        return RespBean.success(hashMap);
    }

    /**
     * 获取上传过来的文件，把文件内容读取出来，导入到数据库。
     * @param file
     * @return
     */
    @PostMapping("/import")
    public RespBean addSubject(MultipartFile file) {
        eduSubjectService.savaSubject(file,eduSubjectService);
        return RespBean.success();
    }
}
