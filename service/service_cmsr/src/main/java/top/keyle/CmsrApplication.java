package top.keyle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author TMJIE5200
 * @date 2023-02-07 21:35:02
 * @week 星期二
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CmsrApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsrApplication.class, args);
    }
}
