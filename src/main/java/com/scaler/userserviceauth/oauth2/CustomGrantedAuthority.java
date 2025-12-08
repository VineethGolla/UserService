package com.scaler.userserviceauth.oauth2;

import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return "";
    }
}
