package com.gerenciamento.projetoapi.repository;

import com.gerenciamento.projetoapi.model.Membro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembroRepository extends JpaRepository<Membro, Long> {
}
