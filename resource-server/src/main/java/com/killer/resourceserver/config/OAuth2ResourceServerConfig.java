package com.killer.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * <p>
 *     资源服务配置
 * </p>
 * @author killer
 * @date 2019/07/24 - 21:54
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/api/**") // 该filter过滤api接口
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        // 从同一个redis中获取token, 将token存redis中是因为快。其实在现有的状态下根本没有影响。
        resources.tokenStore(new RedisTokenStore(lettuceConnectionFactory()));
        resources.resourceId("").stateless(true);
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        RedisStandaloneConfiguration configuration = lettuceConnectionFactory.getStandaloneConfiguration();
        configuration.setHostName("47.107.106.214");
        configuration.setDatabase(3);
        configuration.setPassword("killerWqs");
        return lettuceConnectionFactory;
    }
}
