package cn.killers.wqscasserver.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Wu QiaoSheng
 * @description
 * @date 2019-12-27 16:16
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 针对/token进行认证
        http.requestMatchers().antMatchers("/token")
                .and()
                .authorizeRequests().antMatchers("/token").authenticated()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/login").permitAll();
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        RedisStandaloneConfiguration configuration = lettuceConnectionFactory.getStandaloneConfiguration();
        configuration.setHostName("47.107.106.214");
        configuration.setDatabase(10);
        configuration.setPassword("killerWqs");
        return lettuceConnectionFactory;
    }

}
