package com.killer.clientserver.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author killer
 * @date 2019/08/25 - 14:00
 */
@MapperScan("com.killer.clientserver.modules.*.mapper")
@Configuration
public class MybatisPlusConfig {

}
