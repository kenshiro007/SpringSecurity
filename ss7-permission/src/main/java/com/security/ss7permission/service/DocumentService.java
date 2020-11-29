package com.security.ss7permission.service;

import com.security.ss7permission.model.Document;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
public class DocumentService {


    @PostAuthorize("hasPermission(returnObject, 'read')")
    public List<Document> findDocuments(@PathVariable String username) {
        var doc = new Document();
        doc.setUser("john");
        return List.of(doc);
    }
}
