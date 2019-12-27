package com.killer.clientserver.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * @author Wu QiaoSheng
 * @description @EnableOAuth2Sso 和 @EnableOAuth2Client
 * @date 2019-12-23 15:46
 */

@Configuration
@EnableOAuth2Sso
@EnableOAuth2Client
public class SecuritySsoConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        http.authorizeRequests().antMatchers("/error").permitAll();

    }
}
