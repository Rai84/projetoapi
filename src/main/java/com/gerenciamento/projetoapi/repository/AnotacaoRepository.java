package com.gerenciamento.projetoapi.repository;

import com.gerenciamento.projetoapi.model.Anotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnotacaoRepository extends JpaRepository<Anotacao, Long> {

    @Query("SELECT a FROM Anotacao a WHERE a.usuario.id_usuario = :id")
    List<Anotacao> findByUsuarioId(@Param("id") Long id);
}
