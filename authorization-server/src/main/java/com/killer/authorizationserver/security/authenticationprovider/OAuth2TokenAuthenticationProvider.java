package com.killer.authorizationserver.security.authenticationprovider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Wu QiaoSheng
 * @description
 * @date 2019-12-10 16:50
 */
public class OAuth2TokenAuthenticationProvider implements AuthenticationProvider {

    private ClientDetailsService clientDetailsService;

    public OAuth2TokenAuthenticationProvider(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String clientId = authentication.getPrincipal().toString();
        ClientDetails client = clientDetailsService.loadClientByClientId(clientId);

        if(client.getClientSecret().equals(authentication.getCredentials())) {
            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), Collections.EMPTY_LIST);
        }

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
