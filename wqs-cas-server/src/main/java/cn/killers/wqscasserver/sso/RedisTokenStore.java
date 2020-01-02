package cn.killers.wqscasserver.sso;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * @author Wu QiaoSheng
 * @description
 * @date 2019-12-31 16:30
 */
public class RedisTokenStore implements TokenStore {

    private LettuceConnectionFactory redisFactory;

    private ObjectMapper objectMapper;

    public RedisTokenStore(LettuceConnectionFactory redisFactory, ObjectMapper objectMapper) {
        this.redisFactory = redisFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public Token allocateToken(Authentication authentication, Token token) throws JsonProcessingException {
        // 生成token 一般 token 由用户信息、时间戳和由 hash 算法加密的签名构成。
        // 根据用户信息获取，根据用户名获取token？？？

        // 能到这边表明token是有效的
        // authentication 存在的情况下 token不一定颁发
        if(token != null) {
            return token;
        }

        CasToken casToken = new CasToken().setKey(UUID.randomUUID().toString())
                .setKeyCreationTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli())
                .setExtendedInformation(authentication.getPrincipal().toString());

        // 存入到redis中使用lettcue
        RedisConnection connection = redisFactory.getConnection();
        connection.set(casToken.getKey().getBytes(), objectMapper.writeValueAsBytes(casToken));

        return casToken;
    }

    @Override
    public Token removeToken(Token token) {
        return null;
    }

    @Override
    public Token getToken() {
        return null;
    }

    private static CasToken generate() {

        return null;
    }
}
