package top.keyle.online_video_learning_system.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import top.keyle.online_video_learning_system.entry.EduSubject;
import top.keyle.online_video_learning_system.entry.excel.SubjectData;
import top.keyle.online_video_learning_system.service.EduSubjectService;
import top.keyle.universal_tool.GlobalException;
import top.keyle.universal_tool.RespBeanEnum;

/**
 * @author TMJIE5200
 * @date 2023-01-20 14:15:13
 * @week 星期五
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GlobalException(RespBeanEnum.ERROR_THE_FILE_DATA_IS_EMPTY);
        }
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if ( existOneSubject == null) {
            //没有相同一级分类，添加进去。
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }
        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if (existTwoSubject == null) {
            //没有相同一级分类，添加进去。
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }
    }
        //判断一级分类不能重复添加
        EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
            QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
            wrapper.eq("title",name);
            wrapper.eq("parent_id","0");
            return eduSubjectService.getOne(wrapper);
        }
        //判断二级分类不能重复添加
        EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
            QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
            wrapper.eq("title",name);
            wrapper.eq("parent_id",pid);
            return eduSubjectService.getOne(wrapper);
        }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
