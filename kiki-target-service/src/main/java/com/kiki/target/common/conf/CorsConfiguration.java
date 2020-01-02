package com.kiki.target.common.conf;

import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author Dengzhi
 * @Version 2017/12/07
 */
@Configuration
public class CorsConfiguration extends WebMvcConfigurerAdapter{
    @Value("${security.cors.enabled}")
    private boolean enabled = false;
    @Value("${security.cors.mappings}")
    private String mappings = "/**";
    @Value("${security.cors.allowedOrigins}")
    private String allowedOrigins;
    @Value("${security.cors.allowedMethods}")
    private String allowedMethods;
    @Value("${security.cors.allowedHeaders}")
    private String allowedHeaders;
    @Value("${security.cors.allowCredentials}")
    private boolean allowCredentials = false;
    @Value("${security.cors.maxAge}")
    private int maxAge = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (enabled) {
            if (mappings != null && mappings.length() > 0) {
                for (String mapping : mappings.split(",")) {
                    CorsRegistration corsRegistration = registry.addMapping(mapping).allowCredentials(allowCredentials).maxAge(maxAge);
                    if (StrUtil.isNotBlank(allowedOrigins)) {
                        corsRegistration.allowedOrigins(allowedOrigins.split(","));
                    }
                    if (StrUtil.isNotBlank(allowedMethods)) {
                        corsRegistration.allowedMethods(allowedMethods.split(","));
                    }
                    if (StrUtil.isNotBlank(allowedHeaders)) {
                        corsRegistration.allowedHeaders(allowedHeaders.split(","));
                    }
                }
            }
        }
    }
}
