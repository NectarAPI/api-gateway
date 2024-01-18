package ke.co.nectar.api.configurations;

import ke.co.nectar.api.validation.EndpointsPermissionEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class ApiWebSecurityConfigurationAdapter {


    private EndpointsPermissionEvaluator permissionEvaluator;

    @Bean
    public EndpointsPermissionEvaluator getCustomPermissionEvaluator() {
        return new EndpointsPermissionEvaluator();
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(getCustomPermissionEvaluator());
        return handler;
    }
}