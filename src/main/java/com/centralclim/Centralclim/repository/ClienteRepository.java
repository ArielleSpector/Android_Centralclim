package com.centralclim.Centralclim.repository;

import com.centralclim.Centralclim.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ClienteRepository extends JpaRepository <Cliente, Long> {
}
