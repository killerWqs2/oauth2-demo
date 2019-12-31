package cn.killers.wqscasserver.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;

import java.util.UUID;

/**
 * @author Wu QiaoSheng
 * @description
 * @date 2019-12-31 16:30
 */
public class RedisTokenStore implements TokenStore {

    @Override
    public Token allocateToken(Authentication authentication) {
        // 生成token 一般 token 由用户信息、时间戳和由 hash 算法加密的签名构成。

        return UUID.randomUUID();
    }

    @Override
    public Token removeToken(Token token) {
        return null;
    }

    @Override
    public Token getToken() {
        return null;
    }
}
