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
@Order(2)
@EnableWebSecurity
public class JWTWebSecurityConfigCustomer extends WebSecurityConfigurerAdapter {

    private final JwtUnauthorizedEntryPoint jwtUnauthorizedEntryPoint;
    private final JwtUserDetailsCustomerService jwtUserDetailsCustomerService;
    private final JwtTokenAuthorizationFilter jwtTokenAuthorizationFilter;

    public JWTWebSecurityConfigCustomer(JwtUnauthorizedEntryPoint jwtUnauthorizedEntryPoint,
                                        JwtUserDetailsCustomerService jwtUserDetailsCustomerService,
                                        JwtTokenAuthorizationFilter jwtTokenAuthorizationFilter) {
        this.jwtUnauthorizedEntryPoint = jwtUnauthorizedEntryPoint;
        this.jwtUserDetailsCustomerService = jwtUserDetailsCustomerService;
        this.jwtTokenAuthorizationFilter = jwtTokenAuthorizationFilter;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsCustomerService).passwordEncoder(passwordEncoderBean());
    }

    @Bean(name = "passwordEncoderCustomer")
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "authenticationManagerCustomer")
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
                .rememberMe().key("rememberKey")
                .rememberMeParameter("remember") //Name of checkbox at login page
                .tokenValiditySeconds(300); // keep for one day
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity
                .ignoring()
                .antMatchers(HttpMethod.POST, "/customer/authenticate")
                .antMatchers(HttpMethod.POST, "/customer/refresh")
                .antMatchers(HttpMethod.OPTIONS, "/**")

                .and()
                .ignoring()
                .antMatchers(HttpMethod.GET, "/meal/findAllByMeal")
                .antMatchers(HttpMethod.GET, "/contact")
                .antMatchers(HttpMethod.GET, "/meal/campaigns")
                .antMatchers(HttpMethod.GET, "/");

    }
}