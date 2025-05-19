package com.gerenciamento.projetoapi.service.details;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gerenciamento.projetoapi.model.Usuario;
import com.gerenciamento.projetoapi.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioDetailsService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Buscando usuário com o email: {}", email);

        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            logger.error("Usuário com o email {} não encontrado", email);
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        logger.info("Usuário encontrado: ID = {}, Email = {}, Nome = {}", usuario.getId_usuario(), usuario.getEmail(),
                usuario.getNome());
        return new UsuarioDetails(usuario);
    }
}
