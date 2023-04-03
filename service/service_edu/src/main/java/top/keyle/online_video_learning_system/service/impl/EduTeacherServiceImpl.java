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

    /**
     * 根据指定条件查询讲师列表并进行分页
     @param page 当前页码
     @param pageSize 每页显示记录数
     @param eduTeacherQuery EduTeacherQuery对象，封装了查询条件
     @return 分页后的EduTeacher对象列表的JsonPage包装类
     */
    @Override
    public JsonPage<EduTeacher> getAListOfInstructorsBasedOnCriteria(Integer page, Integer pageSize, EduTeacherQuery eduTeacherQuery) {
        // 1 使用PageHelper分页插件，指定当前页码和每页显示记录数
        PageHelper.startPage(page, pageSize);
        // 2 创建LambdaQueryWrapper对象，用于指定查询条件
        LambdaQueryWrapper<EduTeacher> wrapper = new LambdaQueryWrapper<>();
        // 3 从eduTeacherQuery对象中获取查询条件
        String name = eduTeacherQuery.getName();
        String begin = eduTeacherQuery.getBegin();
        String end = eduTeacherQuery.getEnd();
        Integer level = eduTeacherQuery.getLevel();
        // 4 根据不同的查询条件，设置LambdaQueryWrapper对象的查询条件
        wrapper.like(!ObjectUtils.isEmpty(name), EduTeacher::getName, name)
                .eq(!ObjectUtils.isEmpty(level), EduTeacher::getLevel, level)
                .ge(!ObjectUtils.isEmpty(begin), EduTeacher::getGmtCreate, begin)
                .le(!ObjectUtils.isEmpty(end), EduTeacher::getGmtModified, end);
        // 5 使用eduTeacherMapper的selectList方法查询满足条件的EduTeacher对象列表
        List<EduTeacher> eduTeacherList = eduTeacherMapper.selectList(wrapper);
        // 6 将EduTeacher对象列表封装到JsonPage包装类中，并返回该包装类
        return JsonPage.restPage(new PageInfo<>(eduTeacherList));
    }

    @Override
    public Boolean updateSelective(EduTeacher eduTeacher) {
        return eduTeacherMapper.updateSelective(eduTeacher) > 0;
    }
}




