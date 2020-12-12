package com.jonnyle.checklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private AdminDetailsService adminDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
            .authorizeRequests()
                .antMatchers("/editCheckIn/**", "/addSchool", "/delete/**").hasRole("ADMIN")
                .antMatchers("/aci", "/css/**","/any").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .permitAll()
                .successForwardUrl("/aciSuccess")
                .and()
            .logout().permitAll().logoutSuccessUrl("/aci");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}