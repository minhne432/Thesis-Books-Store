// WebConfig.java

package com.comestic.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private static String UPLOAD_DIR = System.getProperty("user.dir") + "/product-images";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**") // URL pattern to match
                .addResourceLocations("file:" + UPLOAD_DIR + "/"); // Physical path where images are stored
    }
}
