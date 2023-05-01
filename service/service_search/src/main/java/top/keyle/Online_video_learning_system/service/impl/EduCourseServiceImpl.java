package top.keyle.Online_video_learning_system.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.keyle.Online_video_learning_system.entry.EduCourse;
import top.keyle.Online_video_learning_system.mapper.EduCourseMapper;
import top.keyle.Online_video_learning_system.service.EduCourseService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_course(课程表)】的数据库操作Service实现
* @createDate 2023-05-01 23:24:20
*/
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse>
    implements EduCourseService{

    @Autowired
    ElasticsearchClient client;

    @Value("${elasticsearch.indices-name}")
    private String elasticsearchIndex;

    @Override
    public Date selectMaxModificationTime() {
        return baseMapper.selectMaxGmtCreate();
    }

    @Override
    public void modifyTheIndex() {
        List<EduCourse> eduCourseList = baseMapper.selectList(null);
        // 批量添加文档
        BulkRequest.Builder br = new BulkRequest.Builder();
        for (EduCourse course : eduCourseList) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(elasticsearchIndex)
                            .id(course.getId())
                            .document(course)
                    )
            );
        }
        BulkResponse result = null;
        try {
            result = client.bulk(br.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Log errors, if any
        if (result.errors()) {
            log.error("Bulk had errors");
            for (BulkResponseItem item: result.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        }
    }
}




