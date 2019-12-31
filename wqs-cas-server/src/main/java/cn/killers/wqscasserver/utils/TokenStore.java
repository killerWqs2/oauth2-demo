package cn.killers.wqscasserver.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;

public interface TokenStore {

    Token allocateToken(Authentication authentication);

    Token removeToken(Token token);

    Token getToken();

}
