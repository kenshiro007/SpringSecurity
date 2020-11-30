package com.security.ss7permission.config;


import com.security.ss7permission.evaluator.DocumentMethodAuthManager;
import com.security.ss7permission.evaluator.DocumentPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true, // @PreAuthorize , @PreFilter, @PostAuthorize, @PostFilter
         securedEnabled = true, // @Secured
        jsr250Enabled = true // @RolesAllowed
)
public class ProjectConfig
        extends GlobalMethodSecurityConfiguration {

    @Autowired
    public ApplicationContext applicationContext;

    @Bean
    public UserDetailsService userDetailsService() {
        var uds = new InMemoryUserDetailsManager();

        var u1 = User.withUsername("john")
                .authorities("read")
                .roles("MANAGER")
                .password("123")
                .build();

        var u2 = User.withUsername("bill")
                .authorities("write")
                .roles("ADMIN")
                .password("123")
                .build();

        uds.createUser(u1);
        uds.createUser(u2);

        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        var meh = new DefaultMethodSecurityExpressionHandler();
        meh.setPermissionEvaluator(permissionEvaluator());
        meh.setApplicationContext(applicationContext);
        return meh;
    }

    private PermissionEvaluator permissionEvaluator() {
        return new DocumentPermissionEvaluator();
    }
}
