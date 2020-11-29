package com.oauth.ss4oauthjwt.config;


import com.oauth.ss4oauthjwt.security.JpaClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;


@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    public AuthenticationManager authenticationManager;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // its necessary to precise the password encoder because have being defined one for the client
        security.passwordEncoder(NoOpPasswordEncoder.getInstance());
        security.tokenKeyAccess("isAuthenticated()"); // isAuthenticated() permitAll()
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(cds());
        /*clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read")
                .and()
                .withClient("resourceserver")
                .secret("resourceserversecret");*/
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(converter());
    }

    @Bean
    public JwtAccessTokenConverter converter() {
        var conv = new JwtAccessTokenConverter();
        // symmetric key its not so good because the same key is shared with the resource server
        //conv.setSigningKey("secret");

        KeyStoreKeyFactory keyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("ssia.jks"),
                "ssia123".toCharArray()
        );
        conv.setKeyPair(keyFactory.getKeyPair("ssia"));
        return conv;
    }

    @Bean
    public JpaClientDetailsService cds() {
        return new JpaClientDetailsService();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                 .tokenStore(tokenStore())
                 .accessTokenConverter(converter());
    }
}
