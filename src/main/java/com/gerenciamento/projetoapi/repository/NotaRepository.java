package com.gerenciamento.projetoapi.repository;

import com.gerenciamento.projetoapi.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {

    @Query("SELECT n FROM Nota n WHERE n.usuario.id_usuario = :id")
    Optional<Nota> findByUsuarioId(@Param("id") Long id);
}
