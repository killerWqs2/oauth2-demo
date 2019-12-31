package cn.killers.wqscasserver.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Wu QiaoSheng
 * @description
 * @date 2019-12-31 17:02
 */
@ConfigurationProperties(prefix = "project", ignoreUnknownFields = true, ignoreInvalidFields = false)
@Component
public class ProjectProperties {

    private int token_expires_time;

    public int getToken_Expires_time() {
        return token_expires_time;
    }

    public void setToken_expires_time(int token_expires_time) {
        this.token_expires_time = token_expires_time;
    }

}
