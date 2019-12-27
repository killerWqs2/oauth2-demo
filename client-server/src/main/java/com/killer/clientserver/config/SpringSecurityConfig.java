package com.killer.clientserver.config;

import com.killer.clientserver.common.security.ApiOAuth2ExceptionRender;
import com.killer.clientserver.security.oauth2.converter.message.QQOAuth2AccessTokenResHttpMessageConverter;
import com.killer.clientserver.security.oauth2.userservice.OpenIdUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * @author killer
 * 都快过去四个月了
 * @date 2019/08/11 - 14:09
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 为什么没有管用, 也就是说在到达判断权限的那一步之前就被干掉了
        web.ignoring().antMatchers("/static/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        oAuth2AuthenticationEntryPoint.setExceptionRenderer(new ApiOAuth2ExceptionRender());

        // httpSecurity 是用来构建SecurityFilterChain, websecurity是用来构建FilterChainProxy, FilterChainProxy 可以包含多个SecurityFilterChain
        http
            // .requestMatchers().antMatchers("/oauth/**")
            // .and()
            .authorizeRequests()
            // .antMatchers("/oauth/**", "/api/**").authenticated() oauth2api是不需要认证的，因为会和本身系统的认证冲突，只需要使用session保存信息就好了
            .antMatchers("/oauth/**").authenticated()
                .and()
            .formLogin().loginPage("/static/index.html").loginProcessingUrl("/login").permitAll()
            .and()
            .csrf().disable()
                .exceptionHandling().defaultAuthenticationEntryPointFor(oAuth2AuthenticationEntryPoint, new AntPathRequestMatcher("/api/say"))
            .and()
            .rememberMe().tokenValiditySeconds(2592000);

        // 貌似没有配置从AuthenticationFilter中获取Authentication
        http.oauth2Client()
                .clientRegistrationRepository(clientRegistrationRepository)
                .authorizedClientRepository(new HttpSessionOAuth2AuthorizedClientRepository())
                .authorizationCodeGrant() // 配置授权码模式
                .authorizationRequestResolver(oAuth2AuthorizationRequestResolver(clientRegistrationRepository)); // 用来配置转换oauth2请求

        // http.oauth2Client() 如果有多个第三方资源需要访问

        http.oauth2Login().loginProcessingUrl("/oauth/login").loginPage("/static/index.html")
                .authorizationEndpoint().baseUri("/v1/register")
                .and()
                .redirectionEndpoint().baseUri("/oauth/token") //应该是获取第三方用户token的，而不是oauth token
                .and()
                .tokenEndpoint().accessTokenResponseClient(authorizationCodeTokenResponseClient())
                .and()
                .userInfoEndpoint().userService(openIdUserDetailService());
        // oauth2Login 就是用于第三方登录， oauth2Client 就是用于oauth2通信细节, 开启这个登录界面也会发生变化

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

    @Bean
    public OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository() {
        return new HttpSessionOAuth2AuthorizedClientRepository();
    }

    @Bean
    public OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver(ClientRegistrationRepository registrationRepository) {
        return new DefaultOAuth2AuthorizationRequestResolver(registrationRepository, "/api/say");
    }

    // @Bean
    // public OAuth2AuthorizedClientManager authorizedClientManager(
    //         ClientRegistrationRepository clientRegistrationRepository,
    //         OAuth2AuthorizedClientRepository authorizedClientRepository) {
    //
    //     OAuth2AuthorizedClientProvider authorizedClientProvider =
    //             OAuth2AuthorizedClientProviderBuilder.builder()
    //                     .authorizationCode()
    //                     .refreshToken()
    //                     .clientCredentials()
    //                     .password()
    //                     .build();
    //
    //     DefaultOAuth2AuthorizedClientManager authorizedClientManager =
    //             new DefaultOAuth2AuthorizedClientManager(
    //                     clientRegistrationRepository, authorizedClientRepository);
    //     authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
    //
    //     return authorizedClientManager;
    // }

    /**以下Bean用于OAuth2Login*/
    @Bean("MyAccessTokenResponseClient")
    public DefaultAuthorizationCodeTokenResponseClient authorizationCodeTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient tokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        RestTemplate restTemplate = new RestTemplate();

        ArrayList<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
        httpMessageConverters.add(auth2AccessTokenResponseHttpMessageConverter());
        httpMessageConverters.add(new FormHttpMessageConverter());
        httpMessageConverters.add(new OAuth2AccessTokenResponseHttpMessageConverter());

        // qq的返回值不是json而是纯文本
        restTemplate.setMessageConverters(httpMessageConverters);
        tokenResponseClient.setRestOperations(restTemplate);
        return tokenResponseClient;
    }

    @Bean("QQOAuth2AccessTokenResponseHttpMessageConverter")
    public QQOAuth2AccessTokenResHttpMessageConverter auth2AccessTokenResponseHttpMessageConverter() {
        QQOAuth2AccessTokenResHttpMessageConverter converter = new QQOAuth2AccessTokenResHttpMessageConverter();
        ArrayList<MediaType> types = new ArrayList<>();
        types.add(MediaType.TEXT_HTML);

        converter.setSupportedMediaTypes(types);
        return converter;
    }


    @Bean("openIdUserDetailService")
    public OpenIdUserDetailService openIdUserDetailService() {
        return new OpenIdUserDetailService();
    }

    public static void main(String[] args) {
        ArrayList<Integer> nodes = new ArrayList<>();
        nodes.add(1);
        int i = 0;
        System.out.println(nodes.stream().filter(item -> item > 123).mapToInt(item -> item).sum());
    }
}
