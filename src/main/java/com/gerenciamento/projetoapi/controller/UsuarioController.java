package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Usuario;
import com.gerenciamento.projetoapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.criarUsuario(usuario);
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario buscarUsuario(@PathVariable Long id) {
        return usuarioService.buscarUsuario(id);
    }

    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.atualizarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }

    // Rotas para Thymeleaf (Retornam Views)
    @Controller
    @RequestMapping("/usuarios")
    public class UsuarioViewController {

        @GetMapping("/listar")
        public String listarUsuariosPage() {
            return "usuario/listar-usuarios";
        }

        @GetMapping("/novo")
        public String criarUsuarioPage() {
            return "usuario/criar-usuario";
        }

        @GetMapping("/editar/{id}")
        public String editarUsuarioPage(@PathVariable Long id) {
            return "usuario/editar-usuario";
        }
    }
}
