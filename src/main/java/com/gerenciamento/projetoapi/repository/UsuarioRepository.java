package com.gerenciamento.projetoapi.repository;

import com.gerenciamento.projetoapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}


