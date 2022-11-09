package top.keyle.online_video_learning_system.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMJIE5200
 * @date 2022-11-09 21:41:29
 * @week 星期三
 */
@Configuration
@MapperScan(value = "top.keyle.online_video_learning_system.mapper")
public class MybatisPlusConfig {
    /**
     * todo : mybatis 分页插件
     * @return PaginationInnerInterceptor
     */
    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        return new PaginationInnerInterceptor();
    }
}
