package top.keyle.online_video_learning_system.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.keyle.online_video_learning_system.entry.EduSubject;
import top.keyle.online_video_learning_system.entry.excel.SubjectData;
import top.keyle.online_video_learning_system.listener.SubjectExcelListener;
import top.keyle.online_video_learning_system.mapper.EduSubjectMapper;
import top.keyle.online_video_learning_system.service.EduSubjectService;
import top.keyle.universal_tool.GlobalException;
import top.keyle.universal_tool.RespBeanEnum;

import java.io.IOException;
import java.io.InputStream;

/**
* @author TMJIE5200
* @description 针对表【edu_subject(课程科目表)】的数据库操作Service实现
* @createDate 2023-01-20 13:45:53
*/
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject>
    implements EduSubjectService {
    /**
    添加课程分类
    * */
    @Override
    public void savaSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            InputStream fileInputStream = file.getInputStream();
            EasyExcel.read(fileInputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            throw new GlobalException(RespBeanEnum.ERROR);
        }

    }
}




