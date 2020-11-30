package com.security.ss7permission.service;

import com.security.ss7permission.model.Document;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@Service
public class DocumentService {


    //@PostAuthorize("hasPermission(returnObject, 'read')")
    //@PostAuthorize("@documentMethodAuthManager.applySecurityPermission(returnObject, 'read')")
    //@Secured("ROLE_MANAGER")
    @RolesAllowed("ROLE_MANAGER")
    public List<Document> findDocuments(@PathVariable String username) {
        var doc = new Document();
        doc.setUser("john");
        return List.of(doc);
    }
}
