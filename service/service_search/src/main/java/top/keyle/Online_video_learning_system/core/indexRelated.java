package top.keyle.Online_video_learning_system.core;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class indexRelated {
    @Autowired
    ElasticsearchClient elasticsearchClient;

    /**
     * 创建索引
     * @param indexName
     * @throws IOException
     */
    public void testLambaIndex(String indexName) throws IOException {
        ElasticsearchIndicesClient indices = elasticsearchClient.indices();
        boolean flag = indices.exists(request -> request.index(indexName)).value();
        if (flag) {
            System.out.println("索引已经存在");
        } else {
            CreateIndexResponse createIndexResponse = indices.create(request -> request.index(indexName));
            System.out.println("创建索引:" + createIndexResponse.acknowledged());
        }

        flag = indices.exists(r -> r.index(indexName)).value();
        if (flag) {
            System.out.println("索引已经存在");
        }
        DeleteIndexResponse deleteIndexResponse = indices.delete(r -> r.index(indexName));
        System.out.println("删除索引：" + deleteIndexResponse.acknowledged());
    }



}
