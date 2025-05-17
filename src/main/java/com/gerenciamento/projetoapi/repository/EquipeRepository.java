package com.gerenciamento.projetoapi.repository;

import com.gerenciamento.projetoapi.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {
}
