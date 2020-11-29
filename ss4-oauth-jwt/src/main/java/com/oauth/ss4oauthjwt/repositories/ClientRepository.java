package com.oauth.ss4oauthjwt.repositories;

import com.oauth.ss4oauthjwt.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByClientId(String client_id);
}
