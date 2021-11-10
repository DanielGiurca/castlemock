package com.castlemock.service.core.system;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoggedInUserProvider {
    public String getLoggedInUsername(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "Unknown";
        }
        return authentication.getName();
    }
}
