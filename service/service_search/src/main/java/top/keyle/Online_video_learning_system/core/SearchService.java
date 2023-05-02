package top.keyle.Online_video_learning_system.core;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.Online_video_learning_system.entry.CourseInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SearchService {

    @Autowired
    ElasticsearchClient elasticsearchClient;

    public HashMap<String, Object> searchForDocuments(String searchText,String index,Integer current,Integer pageSize){
        SearchResponse<CourseInfo> search = null;

        try {
            search = elasticsearchClient.search(s -> s
                            .index(index)
                            .query(q -> q.bool(
                                    builder -> builder.should(
                                            builder1 -> builder1.multiMatch(
                                                    builder2 -> builder2.fields("title","teacherName")
                                                            .boost(2.0f)
                                                            .operator(Operator.Or)
                                                            .query(searchText)
                                                            .analyzer("standard")
                                                            .slop(4)
                                            )
                                    )
                            ))
                            .from((current - 1) * pageSize)
                            .size(pageSize)
                            .highlight(highlightBuilder -> highlightBuilder
                                    .preTags("<font color='red'>")
                                    .postTags("</font>")
                                    .requireFieldMatch(false) //多字段时，需要设置为false
                                    .fields("title", highlightFieldBuilder -> highlightFieldBuilder)
                                    .fields("teacherName", highlightFieldBuilder -> highlightFieldBuilder)
                            ),
                    CourseInfo.class
            );
        } catch (IOException e) {
            log.error("查询出现异常，请检查！");
            throw new RuntimeException(e);
        }
        TotalHits total = search.hits().total();
        boolean isExactResult = false;
        if (total != null) {
            isExactResult = total.relation() == TotalHitsRelation.Eq;
        }
        if (isExactResult) {
            log.info("There are " + total.value() + " results");
        } else {
            log.info("There are more than " + total.value() + " results");
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        ArrayList<CourseInfo> arrayList = new ArrayList<>();
        for (Hit<CourseInfo> pro : search.hits().hits()) {
            CourseInfo courseInfo = pro.source();
            Map<String, List<String>> highlight = pro.highlight();
            courseInfo.setHighLight(highlight);
            arrayList.add(courseInfo);
        }
        hashMap.put("eduCourseList", arrayList);
        hashMap.put("totalCount", total.value());
        hashMap.put("currentPage", current);
        hashMap.put("pageSize", pageSize);
        return hashMap;
    }
}
