package com.gamitology.kevent.kevent.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomTokenConverter extends DefaultAccessTokenConverter {

    private UserAuthenticationConverter userAuthenticationConverter = new CustomUserAuthenticationConverter();

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication oAuth2Authentication = super.extractAuthentication(claims);
        oAuth2Authentication.setDetails(claims);

        Authentication authentication = userAuthenticationConverter.extractAuthentication(claims);
        OAuth2Authentication customAuthentication = new OAuth2Authentication(oAuth2Authentication.getOAuth2Request(), authentication);
        return customAuthentication;
    }


}
