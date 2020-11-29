package com.security;

import com.security.ss7permission.model.Document;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

public class DocumentPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetObject, Object permission) {
        List<Document> returnedList = (List<Document>) targetObject;
        String auth = (String) permission;
        String username = authentication.getName();
        boolean docsBelongToTheAuthUser = returnedList.stream()
                .allMatch(d -> d.getUser().equals(username));

        boolean hasProperAuth = authentication.getAuthorities()
                .stream()
                .anyMatch(g -> g.getAuthority().equals(auth));
        return docsBelongToTheAuthUser && hasProperAuth;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetID, String s, Object permission) {
        return false;
    }
}
