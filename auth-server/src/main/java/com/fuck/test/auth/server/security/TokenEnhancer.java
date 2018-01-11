package com.fuck.test.auth.server.security;

import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DESCRIPTION:
 * token返回至添加userid
 * @author zouyan
 * @create 2017-12-22 16:12
 * Created by fuck~ on 2017/12/22.
 */
public class TokenEnhancer extends JwtAccessTokenConverter implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String TOKEN_USER_ID = "userId";

    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        RemoteUserDetails userDetails = (RemoteUserDetails) authentication.getPrincipal();
        Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
        info.put(TOKEN_USER_ID, userDetails.getUserId());
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        defaultOAuth2AccessToken.setAdditionalInformation(info);
        OAuth2AccessToken enhancedToken = super.enhance(defaultOAuth2AccessToken, authentication);
        return enhancedToken;
    }
}
