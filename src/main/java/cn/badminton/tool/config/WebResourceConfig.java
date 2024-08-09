package cn.badminton.tool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebResourceConfig implements WebMvcConfigurer {
    /**
     * 静态资源处理
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        // registry.addResourceHandler("/p/**").addResourceLocations("file:" + "F:/");
//        registry.addResourceHandler("/xcx01-images/**").addResourceLocations("file:/home/x/Pictures/ProjectUploadFile/xcx01-images");

    }
}
