package com.codingstrain.springcloud.sample.libraryapp.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

//This class is needed to forward the access token from one service the other and it is used by the OAuthFeignConfig class
//In this sample we keep simple and consider only one service, so this is not required. It is just a remainder
public class OAuthAccessTokenManager {

    private static final Logger logger = LoggerFactory.getLogger(OAuthAccessTokenManager.class);

    public String getAccessToken() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
            String tokenValue = ((JwtAuthenticationToken) authentication).getToken()
                .getTokenValue();
            return tokenValue;

        } catch (Exception exp) {
            logger.error("client credentials error " + exp.getMessage());
        }
        return null;
    }
}