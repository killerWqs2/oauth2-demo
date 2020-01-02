package cn.killers.wqscasserver.sso;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;

public interface TokenStore {

    Token allocateToken(Authentication authentication, Token token) throws JsonProcessingException;

    Token removeToken(Token token);

    Token getToken();

}
