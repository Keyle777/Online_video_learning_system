package top.keyle.Online_video_learning_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.Online_video_learning_system.client.UcenterClient;
import top.keyle.Online_video_learning_system.entry.EduComment;
import top.keyle.Online_video_learning_system.entry.UcenterMember;
import top.keyle.Online_video_learning_system.mapper.EduCommentMapper;
import top.keyle.Online_video_learning_system.service.EduCommentService;
import top.keyle.universal_tool.JsonPage;

import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_comment(评论表)】的数据库操作Service实现
* @createDate 2023-02-12 15:03:15
*/
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment>
    implements EduCommentService{
    @Autowired
    EduCommentMapper eduCommentMapper;

    @Autowired
    private UcenterClient ucenterClient;

    /**
     分页查询课程评论列表
     @param page 当前页码
     @param pageSize 每页数量
     @param id 课程ID
     @return 包含分页信息和课程评论列表的JsonPage对象
     */
    @Override
    public JsonPage<EduComment> paginateToGetAListOfInstructors(Integer page, Integer pageSize,String id) {
        // 使用PageHelper进行分页处理
        PageHelper.startPage(page, pageSize);
        // 使用QueryWrapper封装查询条件
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(id),"course_id",id);
        // 查询课程评论列表
        List<EduComment> EduCommentList = eduCommentMapper.selectList(wrapper);
        for (EduComment eduComment : EduCommentList) {
            UcenterMember info = ucenterClient.getInfo(eduComment.getMemberId());
            // 设置评论昵称和头像
            eduComment.setNickname(info.getNickname());
            eduComment.setAvatar(info.getAvatar());
        }
        // 将查询结果封装成JsonPage对象返回
        return JsonPage.restPage(new PageInfo<>(EduCommentList));
    }



    /**
     保存评论信息
     @param memberId 会员ID
     @param comment 评论对象
     @return 保存成功返回 true，否则返回 false
     */
    @Override
    public boolean SaveComment(String memberId, EduComment comment) {
        comment.setCourseId(comment.getCourseId());
        comment.setTeacherId(comment.getTeacherId());
        comment.setMemberId(memberId);
        // 插入评论记录到数据库
        return eduCommentMapper.insert(comment) > 0;
    }
}




