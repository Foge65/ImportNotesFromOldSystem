package com.cleverdevsoftware.oldsystem.repository;

import com.cleverdevsoftware.oldsystem.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
