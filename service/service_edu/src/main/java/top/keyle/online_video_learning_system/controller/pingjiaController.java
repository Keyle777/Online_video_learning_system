package top.keyle.online_video_learning_system.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.online_video_learning_system.entry.EduComment;
import top.keyle.online_video_learning_system.mapper.EduCommentMapper;
import top.keyle.universal_tool.RespBean;

import java.util.Date;

/**
 * @author TMJIE5200
 * @date 2023-01-19 22:33:31
 * @week 星期四
 */
@RestController
@RequestMapping("/eduService/pingjia")
@Api(tags = {"评价管理"})
@CrossOrigin
public class pingjiaController {
    @Autowired
    EduCommentMapper eduCommentMapper;
    @GetMapping("/findAllpingjia")
    public RespBean findAllTeacher(String rating,String content) {
        EduComment eduComment = new EduComment();
        eduComment.setContent(content);
        eduComment.setStar(rating);
        eduComment.setCourseId("11");
        eduComment.setTeacherId("1189389726308478977");
        eduComment.setGmtCreate(new Date());
        eduComment.setMemberId("04b4872ca02f4a89a80");
        eduComment.setGmtModified(new Date());
        eduComment.setNickname("苏思源");
        eduCommentMapper.insert(eduComment);
        return RespBean.success();
    }
}
