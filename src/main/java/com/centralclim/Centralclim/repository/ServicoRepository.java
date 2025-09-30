package com.centralclim.Centralclim.repository;

import com.centralclim.Centralclim.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ServicoRepository extends JpaRepository <Servico, Long> {
}
