package com.duoc.ms_admin.config;

import com.duoc.ms_admin.security.AdminRoleFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AdminRoleFilter> adminRoleFilterRegistration(AdminRoleFilter filter) {
        FilterRegistrationBean<AdminRoleFilter> registration = new FilterRegistrationBean<>(filter);
        registration.addUrlPatterns("/admin/rutinas/*", "/admin/ejercicios/*", "/admin/insights/*");
        return registration;
    }
}