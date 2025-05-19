package com.gerenciamento.projetoapi.repository;

import com.gerenciamento.projetoapi.model.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PapelRepository extends JpaRepository<Papel, Long> {
}
