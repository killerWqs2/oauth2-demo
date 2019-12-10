package com.killer.clientserver.security.oauth2.userservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.killer.clientserver.modules.sys.entity.User;
import com.killer.clientserver.modules.sys.service.IUserService;
import com.killer.clientserver.security.oauth2.converter.http.OAuth2OpenIdUserRequestEntityConverter;
import com.killer.clientserver.security.oauth2.converter.http.OAuth2UserInfoRequestEntityConverter;
import com.killer.clientserver.security.oauth2.converter.message.QQOAuth2OpenIdResHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author killer
 * @date 2019/08/24 - 12:00
 */
public class OpenIdUserDetailService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String MISSING_USER_INFO_URI_ERROR_CODE = "missing_user_info_uri";

    private static final String MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE = "missing_user_name_attribute";

    private Converter<OAuth2UserRequest, RequestEntity<?>> openIdRequestEntityConverter = new OAuth2OpenIdUserRequestEntityConverter();

    private Converter<OAuth2UserRequest, RequestEntity<?>> userInfoRequestEntityConverter = new OAuth2UserInfoRequestEntityConverter();

    private RestOperations restOperations;

    private static final String INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response";

    private static final ParameterizedTypeReference<String> PARAMETERIZED_OPENID_RESPONSE_TYPE =
            new ParameterizedTypeReference<String>() {
            };

    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_USERINFO_RESPONSE_TYPE =
            new ParameterizedTypeReference<Map<String, Object>>() {
            };

    public OpenIdUserDetailService() {
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<HttpMessageConverter<?>> converters = new ArrayList<>();
        QQOAuth2OpenIdResHttpMessageConverter qqoAuth2OpenIdResHttpMessageConverter = new QQOAuth2OpenIdResHttpMessageConverter();
        ArrayList<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_HTML);
        qqoAuth2OpenIdResHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        MappingJackson2HttpMessageConverter qqoAuth2UserInfoResHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        qqoAuth2UserInfoResHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        converters.add(qqoAuth2OpenIdResHttpMessageConverter);
        converters.add(qqoAuth2UserInfoResHttpMessageConverter);
        restTemplate.setMessageConverters(converters);
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        this.restOperations = restTemplate;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        if (!StringUtils.hasText(userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri())) {
            OAuth2Error oauth2Error = new OAuth2Error(
                    MISSING_USER_INFO_URI_ERROR_CODE,
                    "Missing required UserInfo Uri in UserInfoEndpoint for Client Registration: " +
                            userRequest.getClientRegistration().getRegistrationId(),
                    null
            );
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }

        if (!StringUtils.hasText(userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName())) {
            OAuth2Error oauth2Error = new OAuth2Error(
                    MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE,
                    "Missing required \"user name\" attribute name in UserInfoEndpoint for Client Registration: " +
                            userRequest.getClientRegistration().getRegistrationId(),
                    null
            );
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }

        RequestEntity<?> openIdRequest = this.openIdRequestEntityConverter.convert(userRequest);

        ResponseEntity<String> openIdResponse;
        ResponseEntity<Map<String, Object>> userInfoResponse;
        try {
            openIdResponse = this.restOperations.exchange(openIdRequest, PARAMETERIZED_OPENID_RESPONSE_TYPE);

            // 1.这里需要判断用户是否在数据库中已存在， 存在直接获取用户信息， 不存在直接获取并且需要注册

            // 用户未注册， 使用第三方登录
            HashMap<String, String> params = (HashMap<String, String>) userRequest.getAdditionalParameters().get("params");
            params.put("openId", openIdResponse.getBody());
            RequestEntity<?> userInfoRequest = this.userInfoRequestEntityConverter.convert(userRequest);
            userInfoResponse = this.restOperations.exchange(userInfoRequest, PARAMETERIZED_USERINFO_RESPONSE_TYPE);


            // 获取用户信息
        } catch (OAuth2AuthorizationException ex) {
            OAuth2Error oauth2Error = ex.getError();
            StringBuilder errorDetails = new StringBuilder();

            errorDetails.append("Error details: [");
            errorDetails.append("UserInfo Uri: ").append(
                    userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri());
            errorDetails.append(", Error Code: ").append(oauth2Error.getErrorCode());
            if (oauth2Error.getDescription() != null) {
                errorDetails.append(", Error Description: ").append(oauth2Error.getDescription());
            }
            errorDetails.append("]");
            oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                    "An error occurred while attempting to retrieve the UserInfo Resource: " + errorDetails.toString(), null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        } catch (RestClientException ex) {
            OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                    "An error occurred while attempting to retrieve the UserInfo Resource: " + ex.getMessage(), null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        }

        Map<String, Object> userAttributes = userInfoResponse.getBody();
        Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));

        return new DefaultOAuth2User(authorities, userAttributes, userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName());
    }
}
