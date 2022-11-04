package top.keyle.online_video_learning_system;

import cn.xuyanwu.spring.file.storage.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFileStorage
@MapperScan(value = "top.keyle.online_video_learning_system.mapper")
public class OnlineVideoLearningSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineVideoLearningSystemApplication.class, args);
    }
}
