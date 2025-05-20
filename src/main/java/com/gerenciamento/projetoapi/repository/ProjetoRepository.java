package com.gerenciamento.projetoapi.repository;

import com.gerenciamento.projetoapi.model.Projeto;
import com.gerenciamento.projetoapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    @Query("SELECT p FROM Projeto p WHERE p.usuario.id_usuario = :idUsuario")
    List<Projeto> findByUsuarioIdUsuario(@Param("idUsuario") Long idUsuario);
}
