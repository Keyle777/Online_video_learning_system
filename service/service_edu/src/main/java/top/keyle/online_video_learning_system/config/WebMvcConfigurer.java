package top.keyle.online_video_learning_system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * @author TMJIE5200
 * @date 2022-09-28 14:39:40
 * @week 星期三
 */
@Configuration
@EnableCaching
@MapperScan("top.keyle.demo1.mapper")
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        org.springframework.web.servlet.config.annotation.WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/cors/**")
                .allowedHeaders("*")
                .allowedMethods("POST","GET","DELETE","PUT")
                .allowedOrigins("*");
    }
}
