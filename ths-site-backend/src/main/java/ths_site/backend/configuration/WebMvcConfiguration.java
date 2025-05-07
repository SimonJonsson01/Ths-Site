package ths_site.backend.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfiguration.class);


    @Value("${ths-site.cors.allowed-origins}")
    private String corsAllowrdOrigin;

    /* 
     * This function adds the corsAllowrdOrigin value (application.properties) as an allowed origin for CORS. (Allows frontend to communicate to backend.)
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsAllowrdOrigin)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
                LOGGER.info("Added " + corsAllowrdOrigin + "as allowed origin");
                

    }
};
