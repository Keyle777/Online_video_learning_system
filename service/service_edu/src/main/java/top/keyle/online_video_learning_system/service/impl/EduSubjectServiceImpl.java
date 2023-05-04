package top.keyle.online_video_learning_system.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.keyle.online_video_learning_system.entry.EduSubject;
import top.keyle.online_video_learning_system.entry.excel.SubjectData;
import top.keyle.online_video_learning_system.entry.vo.subject.OneSubject;
import top.keyle.online_video_learning_system.entry.vo.subject.TwoSubject;
import top.keyle.online_video_learning_system.listener.SubjectExcelListener;
import top.keyle.online_video_learning_system.mapper.EduSubjectMapper;
import top.keyle.online_video_learning_system.service.EduSubjectService;
import top.keyle.universal_tool.GlobalException;
import top.keyle.universal_tool.RespBeanEnum;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TMJIE5200
 * @description 针对表【edu_subject(课程科目表)】的数据库操作Service实现
 * @createDate 2023-01-20 13:45:53
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject>
        implements EduSubjectService {
    @Autowired
    EduSubjectMapper eduSubjectMapper;

    /**
     保存学科信息
     @param file 上传的Excel文件
     @param eduSubjectService EduSubjectService对象，用于保存学科信息
     */
    @Override
    public void savaSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            // 1 获取上传的Excel文件流
            InputStream fileInputStream = file.getInputStream();
            // 2 使用EasyExcel读取Excel文件，创建SubjectExcelListener监听器实例，用于处理读取的数据
            EasyExcel.read(fileInputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            // 3 如果出现异常，抛出GlobalException
            throw new GlobalException(RespBeanEnum.ERROR);
        }
    }

    /**
     * 课程分类列表
     *
     * @return 课程分类列表
     */
    @Override
    public Map<String, Object> nestedList() {
        //查找所有的一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneList = eduSubjectMapper.selectList(wrapperOne);
        //查找所有的二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoList = eduSubjectMapper.selectList(wrapperTwo);
        ArrayList<Map<String, Object>> finalList = new ArrayList<>();
        //封装一级分类 查询一级分类的list集合 得到每一个一级对象 获取每个一级分类的值
        for (EduSubject eduSubject : oneList) {
            Map<String, Object> oneSubject = new HashMap<>();
            //把EduSubject中的值封装到OneSubject
            oneSubject.put("name", eduSubject.getTitle());
            //封装二级分类
            String id = eduSubject.getId();
            ArrayList<Map<String, Object>> twoSubjectList = new ArrayList<>();
            for (EduSubject eduSubject1 : twoList) {
                //得到父id
                String parentId = eduSubject1.getParentId();
                if (id.equals(parentId)) {
                    Map<String, Object> twoSubject = new HashMap<>();
                    twoSubject.put("name", eduSubject1.getTitle());
                    twoSubject.put("value", eduSubject1.getSort());
                    twoSubjectList.add(twoSubject);
                }
            }
            oneSubject.put("children", twoSubjectList);
            finalList.add(oneSubject);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("name", "课程类别");
        result.put("children", finalList);
        return result;
    }


    /**
     * 课程分类列表
     *
     * @return 课程分类列表
     */
    @Override
    public List<OneSubject> nestedListTwo() {
        //查找所有的一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneList = eduSubjectMapper.selectList(wrapperOne);
        //查找所有的二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoList = eduSubjectMapper.selectList(wrapperTwo);
        ArrayList<OneSubject> finalList = new ArrayList<>();
        //封装一级分类 查询一级分类的list集合 得到每一个一级对象 获取每个一级分类的值
        for (EduSubject eduSubject : oneList) {
            OneSubject oneSubject = new OneSubject();
            //把EduSubject中的值封装到OneSubject
            BeanUtils.copyProperties(eduSubject, oneSubject);
            //封装二级分类
            String id = oneSubject.getId();
            ArrayList<TwoSubject> twoSubjectList = new ArrayList<>();
            for (EduSubject eduSubject1 : twoList) {
                //得到父id
                String parentId = eduSubject1.getParentId();
                if (id.equals(parentId)) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject1, twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoSubjectList);
            finalList.add(oneSubject);
        }
        return finalList;
    }
}




