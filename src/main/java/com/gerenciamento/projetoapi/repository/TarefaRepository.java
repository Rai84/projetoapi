package com.gerenciamento.projetoapi.repository;

import com.gerenciamento.projetoapi.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
