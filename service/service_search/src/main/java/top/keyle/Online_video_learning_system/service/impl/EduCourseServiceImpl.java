package top.keyle.Online_video_learning_system.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.keyle.Online_video_learning_system.entry.CourseInfo;
import top.keyle.Online_video_learning_system.entry.EduCourse;
import top.keyle.Online_video_learning_system.mapper.EduCourseMapper;
import top.keyle.Online_video_learning_system.service.EduCourseService;

import javax.annotation.Resource;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_course(课程表)】的数据库操作Service实现
* @createDate 2023-05-01 23:24:20
*/
@Service
@Slf4j
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse>
    implements EduCourseService{

    @Resource
    ElasticsearchClient elasticsearchClient;

    @Override
    public Date selectMaxModificationTime() {
        return baseMapper.selectMaxGmtCreate();
    }

    @Override
    public void modifyTheIndex(String elasticsearchIndex) throws IOException {
        List<EduCourse> eduCourseList = baseMapper.selectList(null);
        // 批量添加文档
        BulkRequest.Builder br = new BulkRequest.Builder();
        for (EduCourse course : eduCourseList) {
            String teacherName = baseMapper.selectTeacherName(course.getTeacherId());
            CourseInfo courseInfo = new CourseInfo();
            BeanUtils.copyProperties(course,courseInfo);
            courseInfo.setTeacherName(teacherName);
            br.operations(op -> op
                    .index(idx -> idx
                            .index(elasticsearchIndex)
                            .id(String.valueOf(course.getId()))
                            .document(courseInfo)
                    ));
        }
        // 进行一次批处理
        BulkResponse bulk = elasticsearchClient.bulk(br.build());
        if (bulk.errors()){
            System.out.println("错误");
            throw new RemoteException(bulk.toString());
        }else {
            log.info("已更新数据，更新时间" + new Date());
        }
    }
}




