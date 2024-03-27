package cn.com.v2.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//重写WebMvcConfigurer实现全局跨域配置
@Configuration
public class CorsConfig implements WebMvcConfigurer{
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        	registry.addMapping("/**")
            // 是否发送Cookie
            .allowCredentials(true)
            // 放行哪些原始域
            .allowedOrigins("*")
            // 放行哪些请求方式
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            // 放行哪些原始请求头部信息
            .allowedHeaders("*")
            // 暴露哪些头部信息
            .exposedHeaders("*");
    }
}
