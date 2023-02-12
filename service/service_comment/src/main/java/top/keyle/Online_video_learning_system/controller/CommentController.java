package top.keyle.Online_video_learning_system.controller;

import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.Online_video_learning_system.entry.EduComment;
import top.keyle.Online_video_learning_system.service.EduCommentService;
import top.keyle.universal_tool.JsonPage;
import top.keyle.universal_tool.JwtUtils;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * @author TMJIE5200
 * @date 2023-02-12 15:11:36
 * @week 星期日
 */
@RestController
@RequestMapping("/commentService/comment")
@CrossOrigin
@Api(tags = {"评论管理"})
public class CommentController {
    @Autowired
    private EduCommentService commentService;

    /**
     * 分页查询评论
     * @param page
     * @param limit
     * @param courseId
     * @return
     */
    @GetMapping("{page}/{limit}/{courseId}")
    public RespBean index(@PathVariable Integer page, @PathVariable Integer limit,@PathVariable String courseId){
        // 分页调用
        JsonPage<EduComment> jsonPage = commentService.paginateToGetAListOfInstructors(
                page, limit , courseId);
        return RespBean.success(jsonPage);
    }

    /**
     * 保存用户评论
     * @param comment
     * @param request
     * @return
     */
    @PostMapping("/auth/save")
    public RespBean save(@RequestBody EduComment comment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return RespBean.error(RespBeanEnum.NOT_LOGGED_IN);
        }
        boolean isSave = commentService.SaveComment(memberId, comment);
        if(isSave){
            return RespBean.success();
        }
        return RespBean.error();
    }
}
