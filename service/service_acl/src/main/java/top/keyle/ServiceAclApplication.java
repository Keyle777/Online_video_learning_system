package top.keyle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author OY
 * @Date 2021/4/8
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("top.keyle.Online_video_learning_system")
public class ServiceAclApplication {
     public static void main(String[] args) {
           SpringApplication.run(ServiceAclApplication.class, args);
      }
}
