package ru.skypro.homework.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${file.upload.dir}")
    private String uploadDir;

    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("Upload dir: " + uploadDir);
        // Маппинг URL /uploads/** на локальную папку
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Важно: путь должен заканчиваться на /
        String location = "file:" + uploadDir + "/";
        System.out.println("Configuring static resources at: " + location);

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(location);
    }
}
