package com.security.ss7permission.evaluator;

import com.security.ss7permission.model.Document;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentMethodAuthManager implements AuthorizationRuleManager {

    public boolean applySecurityPermission(List<Document> returnedObject, String permission) {
        String auth = permission;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean docsBelongToTheAuthUser = returnedObject.stream()
                .allMatch(d -> d.getUser().equals(username));

        boolean hasProperAuth = authentication.getAuthorities()
                .stream()
                .anyMatch(g -> g.getAuthority().equals(auth));
        return docsBelongToTheAuthUser && hasProperAuth;
    }
}
