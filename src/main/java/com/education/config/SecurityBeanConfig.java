package com.education.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

/**
 * Бины необходимые дял проверки защищенности методов контроллера
 */
public class SecurityBeanConfig {
    @Bean
    public DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler(
            @Qualifier("defaultPermissionEvaluator") PermissionEvaluator permissionEvaluator) {
        DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        /* устанавливаем наш DefaultPermissionEvaluator в объект methodSecurityExpressionHandler*/
        methodSecurityExpressionHandler.setPermissionEvaluator(permissionEvaluator);
        return methodSecurityExpressionHandler;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler(
            @Qualifier("defaultPermissionEvaluator") PermissionEvaluator permissionEvaluator) {
        DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        webSecurityExpressionHandler.setPermissionEvaluator(permissionEvaluator);
        return webSecurityExpressionHandler;
    }
}

