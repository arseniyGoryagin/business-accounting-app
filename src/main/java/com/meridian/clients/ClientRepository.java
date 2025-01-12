package com.meridian.clients;

import com.meridian.clients.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String address, String name, Pageable pageable);
    Page<Client> findByAddressContainingIgnoreCase(String query, Pageable pageable);
    Page<Client> findByNameContainingIgnoreCase(String query, Pageable pageable);


}
