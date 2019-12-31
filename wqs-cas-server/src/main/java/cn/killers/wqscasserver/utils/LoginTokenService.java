package cn.killers.wqscasserver.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;

/**
 * @author Wu QiaoSheng
 * @description
 * @date 2019-12-31 16:13
 */
public class LoginTokenService implements TokenService {

    private TokenStore tokenStore;

    @Value("#{project.token_expires_time}")
    private int tokenExpiresTime;

    @Override
    public Token allocateToken(String s) {
        // 先查看token是否存在
        Token token = tokenStore.getToken();

        if(token == null) {
            token = tokenStore.allocateToken();
        }

        return null;
    }

    @Override
    public Token verifyToken(String s) {
        return null;
    }

    public TokenStore getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }
}
