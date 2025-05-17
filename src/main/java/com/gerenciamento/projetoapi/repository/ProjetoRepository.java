package com.gerenciamento.projetoapi.repository;

import com.gerenciamento.projetoapi.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
