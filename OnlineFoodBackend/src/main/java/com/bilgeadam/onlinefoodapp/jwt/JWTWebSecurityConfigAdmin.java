package com.bilgeadam.onlinefoodapp.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Order(1)
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JWTWebSecurityConfigAdmin extends WebSecurityConfigurerAdapter {

    private final JwtUnauthorizedEntryPoint jwtUnauthorizedEntryPoint;
    private final JwtUserDetailsAdminService jwtUserDetailsAdminService;
    private final JwtTokenAuthorizationFilter jwtTokenAuthorizationFilter;

    public JWTWebSecurityConfigAdmin(JwtUnauthorizedEntryPoint jwtUnauthorizedEntryPoint, JwtUserDetailsAdminService jwtUserDetailsAdminService, JwtTokenAuthorizationFilter jwtTokenAuthorizationFilter) {
        this.jwtUnauthorizedEntryPoint = jwtUnauthorizedEntryPoint;
        this.jwtUserDetailsAdminService = jwtUserDetailsAdminService;
        this.jwtTokenAuthorizationFilter = jwtTokenAuthorizationFilter;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsAdminService).passwordEncoder(passwordEncoderBean());
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "authenticationManagerAdmin")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtUnauthorizedEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .anyRequest().authenticated();

        httpSecurity
                .addFilterBefore(jwtTokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .headers()
                .frameOptions().sameOrigin()
                .cacheControl();

        httpSecurity
                .rememberMe().key("uniqueAndSecret")
                .rememberMeParameter("remember") //Name of checkbox at login page
                .rememberMeCookieName("rememberlogin") //Cookie name
                .tokenValiditySeconds(300000); //Remember login credentials for number of seconds
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity
                .ignoring()
                .antMatchers(HttpMethod.POST, "/admin/authenticate")
                .antMatchers(HttpMethod.POST, "/admin/refresh")
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.GET, "/");
    }
}