package ru.mirea.springpizzashop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.mirea.springpizzashop.models.Role;
import ru.mirea.springpizzashop.services.UserService;

/**
 * Class for configuration security
 */
@Configuration
@EnableConfigurationProperties
public class SecureConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    /**
     * Method for configuration http protocol
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().cors().disable()
        .authorizeRequests()
        .antMatchers("/", "/home", "/register", "/login", "/css/**", "/img/**", "/product/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin().and().logout().logoutSuccessUrl("/home").and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .sessionFixation().migrateSession();
    }

    /**
     * Method for configuration authentication
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(encoder());
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
