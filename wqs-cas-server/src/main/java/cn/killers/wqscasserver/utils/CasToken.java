package cn.killers.wqscasserver.utils;

import org.springframework.security.core.token.Token;

/**
 * @author Wu QiaoSheng
 * @description
 * @date 2019-12-31 16:51
 */
public class CasToken implements Token {

    @Override
    public String getKey() {
        // key 里面包含用户信息
        return null;
    }

    @Override
    public long getKeyCreationTime() {
        return 0;
    }

    @Override
    public String getExtendedInformation() {
        return null;
    }

}
