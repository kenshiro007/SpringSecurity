package com.oauth.ss4oauthjwt.security;

import com.oauth.ss4oauthjwt.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

public class JpaClientDetailsService implements ClientDetailsService {

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        return clientRepository.findByClientId(s)
                .map(c -> new SecurityClient(c))
                .orElseThrow(() -> new ClientRegistrationException("not found"));
    }
}
