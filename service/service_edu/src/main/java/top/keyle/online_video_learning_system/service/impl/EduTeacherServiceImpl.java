package top.keyle.online_video_learning_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.keyle.online_video_learning_system.entry.EduTeacher;
import top.keyle.online_video_learning_system.entry.vo.eduTeacher.EduTeacherQuery;
import top.keyle.online_video_learning_system.mapper.EduTeacherMapper;
import top.keyle.online_video_learning_system.service.EduTeacherService;
import top.keyle.universal_tool.JsonPage;

import java.util.Collection;
import java.util.List;

/**
 * @author TMJIE5200
 * @description 针对表【edu_teacher(讲师)】的数据库操作Service实现
 * @createDate 2022-11-07 22:05:36
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher>
        implements EduTeacherService {

    @Autowired
    EduTeacherMapper eduTeacherMapper;

    @Override
    public Boolean insertSelective(EduTeacher eduTeacher) {
        return eduTeacherMapper.insertSelective(eduTeacher) > 0;
    }

    @Override
    public Boolean insertBatch(Collection<EduTeacher> eduTeacherCollection) {
        return eduTeacherMapper.insertBatch(eduTeacherCollection) > 0;
    }

    @Override
    public JsonPage<EduTeacher> paginateToGetAListOfInstructors(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<EduTeacher> eduTeacherList = eduTeacherMapper.selectList(null);
        return JsonPage.restPage(new PageInfo<>(eduTeacherList));
    }

    @Override
    public JsonPage<EduTeacher> getAListOfInstructorsBasedOnCriteria(Integer page, Integer pageSize, EduTeacherQuery eduTeacherQuery) {
        PageHelper.startPage(page, pageSize);
        LambdaQueryWrapper<EduTeacher> wrapper = new LambdaQueryWrapper<>();
        String name = eduTeacherQuery.getName();
        String begin = eduTeacherQuery.getBegin();
        String end = eduTeacherQuery.getEnd();
        Integer level = eduTeacherQuery.getLevel();
        wrapper.like(!ObjectUtils.isEmpty(name), EduTeacher::getName, name)
                .eq(!ObjectUtils.isEmpty(level), EduTeacher::getLevel, level)
                .ge(!ObjectUtils.isEmpty(begin), EduTeacher::getGmtCreate, begin)
                .le(!ObjectUtils.isEmpty(end), EduTeacher::getGmtModified, end);
        List<EduTeacher> eduTeacherList = eduTeacherMapper.selectList(wrapper);
        return JsonPage.restPage(new PageInfo<>(eduTeacherList));
    }

    @Override
    public Boolean updateSelective(EduTeacher eduTeacher) {
        return eduTeacherMapper.updateSelective(eduTeacher) > 0;
    }
}




