package cn.killers.wqscasserver.sso;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Token;

/**
 * @author Wu QiaoSheng
 * @description
 * @date 2019-12-31 16:51
 */
public class CasToken implements Token {

    private String key;

    private long keyCreationTime;

    private String extendedInformation;

    @Override
    public String getKey() {
        // key 里面包含用户信息
        return key;
    }

    @Override
    public long getKeyCreationTime() {
        return keyCreationTime;
    }

    @Override
    public String getExtendedInformation() {
        return extendedInformation;
    }

    public CasToken setKey(String key) {
        this.key = key;
        return this;
    }

    public CasToken setKeyCreationTime(long keyCreationTime) {
        this.keyCreationTime = keyCreationTime;
        return this;
    }

    public CasToken setExtendedInformation(String extendedInformation) {
        this.extendedInformation = extendedInformation;
        return this;
    }
}
