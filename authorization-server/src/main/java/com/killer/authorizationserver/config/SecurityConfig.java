package com.killer.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * WebSecurityConfigurer 是用来配置WebSecurity， 像CorsConfigurer 是用来配置HttpSecurity
 * 架构上为什么要这么设计呢？？？
 * @author killer
 * @date 2019/07/27 - 13:25
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 暴露出的AuthenticationManager不是配置的
     */
    // @Bean("myAuthenticationManager")
    // @Override
    // public AuthenticationManager authenticationManagerBean() throws Exception {
    //     return super.authenticationManagerBean();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        http
            // .authorizeRequests().antMatchers("/test/**").authenticated().anyRequest().anonymous()
            .authorizeRequests().anyRequest().authenticated()
            .and()
            // 这里的loginpage到底指向的是什么，不是从DefaultLoginPageGeneratingFilter过来的，下面两个参数应该是暴露出去的
            .formLogin().loginPage("/static/login.html").loginProcessingUrl("/login")
                .failureHandler((request, response, exception) -> response.sendError(10001, "用户名或密码错误")).permitAll()
            // .and()
            // .httpBasic()
            .and()
            .csrf().disable();
        // http.authorizeRequests().antMatchers("/**").permitAll();
        // http.exceptionHandling().authenticationEntryPoint().accessDeniedHandler() 配置 exceptiontranslatorfilter
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.inMemoryAuthentication().withUser("killer").password("wqsqzj");
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("wqsqzj"));

        String a = "a";
        String b = "a";

        System.out.println(a == b);

        System.out.println(null == null);
    }
}
