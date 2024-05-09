package com.codingstrain.springcloud.sample.libraryapp.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

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