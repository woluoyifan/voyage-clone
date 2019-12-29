package com.luoyifan.voyage.config;

import com.luoyifan.voyage.constants.UserOperationEnum;
import com.luoyifan.voyage.controller.filter.AdminFilter;
import com.luoyifan.voyage.controller.filter.UserAccessInfoFilter;
import com.luoyifan.voyage.entity.UserDetails;
import com.luoyifan.voyage.property.VoyageProperty;
import com.luoyifan.voyage.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * @author EvanLuo
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private VoyageProperty voyageProperty;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions().sameOrigin()
                .httpStrictTransportSecurity().disable()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .addFilterBefore(new UserAccessInfoFilter(), SecurityContextPersistenceFilter.class)
                .addFilterBefore(adminFilter(),UserAccessInfoFilter.class)
                .logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler("/"))
                .and()
                .formLogin().loginPage("/").loginProcessingUrl("/").successHandler(authenticationSuccessHandler("/voyage")).failureHandler(authenticationFailureHandler()).permitAll()
                .and()
                .authorizeRequests().antMatchers().permitAll()
                .and().authorizeRequests().antMatchers("/img/**","/favicon.ico","/register","/description","/admin/**").permitAll()
                .anyRequest().authenticated();
    }

    private AdminFilter adminFilter(){
        return new AdminFilter(voyageProperty.getAdminPassword());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected UserDetailsService userDetailsService(){
        return username -> {
            if (StringUtils.isBlank(username)) {
                throw new UsernameNotFoundException("用户名不能为空");
            }
            return userService.getByUsername(username)
                    .map(UserDetails::new)
                    .orElseThrow(()-> new UsernameNotFoundException("用户名或密码错误"));
        };
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler(String redirectUrl){
        return (request, response, authentication) -> {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userService.saveOperation(userDetails.getId(), UserOperationEnum.LOGIN,"登陆游戏",null,null);
            response.sendRedirect(redirectUrl);
        };
    }

    private AuthenticationFailureHandler authenticationFailureHandler(){
        return (httpServletRequest, httpServletResponse, e) -> {
            httpServletRequest.setAttribute("errMsg", "用户名或密码错误");
            httpServletRequest.getRequestDispatcher(httpServletRequest.getRequestURI()).forward(httpServletRequest, httpServletResponse);
        };
    }

    private LogoutSuccessHandler logoutSuccessHandler(String redirectUrl) {
        return ((request, response, authentication) -> {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userService.saveOperation(userDetails.getId(), UserOperationEnum.LOGOUT,"登出游戏",null,null);
            response.sendRedirect(redirectUrl);
        });
    }

}
