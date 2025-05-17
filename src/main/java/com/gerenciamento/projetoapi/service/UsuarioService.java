package com.gerenciamento.projetoapi.service;

import com.gerenciamento.projetoapi.model.Usuario;
import com.gerenciamento.projetoapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
