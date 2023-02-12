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

    @Override
    public JsonPage<EduComment> paginateToGetAListOfInstructors(Integer page, Integer pageSize,String id) {
        PageHelper.startPage(page, pageSize);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(id),"course_id",id);
        List<EduComment> EduCommentList = eduCommentMapper.selectList(wrapper);
        return JsonPage.restPage(new PageInfo<>(EduCommentList));
    }



    @Override
    public boolean SaveComment(String memberId, EduComment comment) {
        comment.setCourseId(comment.getCourseId());
        comment.setTeacherId(comment.getTeacherId());
        comment.setMemberId(memberId);
        UcenterMember info = ucenterClient.getInfo(memberId);
        comment.setNickname(info.getNickname());
        comment.setAvatar(info.getAvatar());
        return eduCommentMapper.insert(comment) > 0;
    }
}




