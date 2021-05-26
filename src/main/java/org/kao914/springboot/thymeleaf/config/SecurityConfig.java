package org.kao914.springboot.thymeleaf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    // Autowired dependencies
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // the sequence matters!!!
                .antMatchers("/").permitAll()
                .antMatchers("/employees/list").permitAll()
                .antMatchers("/employees/search*").permitAll()
                .antMatchers("/employees/showForm*").authenticated()
                .antMatchers("/employees/save").hasAnyRole("MANAGER, ADMIN")
                .antMatchers("/employees/delete").hasAnyRole("ADMIN")
                // any others url should be authorized
                .antMatchers("/**").authenticated()
                .and()
                    // default url /login
                    .formLogin()
                    // to customize login page
//                    .loginPage("/employee/login")
//                    .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
                .and()
                // default url /logout
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                    .exceptionHandling().accessDeniedPage("/access-denied");
    }
}
