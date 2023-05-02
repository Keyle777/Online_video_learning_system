package top.keyle.Online_video_learning_system.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;

/**
 * @author kj_hxx
 * <br/>Date: 2023/3/3
 * <br/>Time: 11:00
 */

@Slf4j
@Component
@Configuration
public class ElasticsearchConfig {


    @Value("${elasticsearch.username}")
    private String userName;

    @Value("${elasticsearch.password}")
    private String password;
    @Value("${elasticsearch.hostname}")
    private String hostname;

    @Value("${elasticsearch.port}")
    private String port;

    @Value("${elasticsearch.scheme}")
    private String scheme;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        String fingerprint = "1F:BD:28:7F:3C:58:44:BA:D2:A3:86:05:7B:75:AE:0F:5F:36:90:9B:31:DE:1A:1A:63:F3:5A:AD:4D:5B:A8:20";

        SSLContext sslContext = TransportUtils
                .sslContextFromCaFingerprint(fingerprint);

        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        // 因为具有权限设置，验证用户
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(userName, password));
        // 1.创建低级客户端
        RestClient builder = RestClient.builder(
                        new HttpHost(hostname, Integer.parseInt(port), scheme))
                .setHttpClientConfigCallback(
                        httpClientBuilder -> httpClientBuilder
                                .setSSLContext(sslContext)
                                .setDefaultCredentialsProvider(credentialsProvider))
                .build();

        // 2.使用 Jackson 映射器创建传输
        ElasticsearchTransport transport = new RestClientTransport(
                builder, new JacksonJsonpMapper());

        // 3.并创建 API 客户端
        ElasticsearchClient elasticsearchClient = new ElasticsearchClient(transport);
        log.info("客户端初始化成功---elasticsearchClient 初始化成功");

        return elasticsearchClient;
    }
}
