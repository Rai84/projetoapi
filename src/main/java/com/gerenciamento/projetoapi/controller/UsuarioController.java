package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Usuario;
import com.gerenciamento.projetoapi.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Listar usuários com paginação
    @GetMapping
    public String listarUsuarios(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Usuario> usuariosPage = usuarioService.listarUsuarios(PageRequest.of(page, 10));
        model.addAttribute("usuarios", usuariosPage.getContent());
        model.addAttribute("usuariosPage", usuariosPage);
        return "usuario/lista"; // templates/usuarios/lista.html
    }

    // Mostrar formulário para novo usuário
    @GetMapping("/novo")
    public String mostrarFormularioCriarUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/novo"; // templates/usuarios/novo.html
    }

    // Salvar novo usuário
    @PostMapping("/salvar")
    public String salvarUsuario(@Valid @ModelAttribute Usuario usuario) {
        usuarioService.salvarUsuario(usuario);
        return "redirect:/usuarios";
    }

    // Mostrar formulário para editar usuário
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarUsuario(@PathVariable Long id, Model model) throws Exception {
        Usuario usuario = usuarioService.buscarUsuario(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
        model.addAttribute("usuario", usuario);
        return "usuario/editar"; // templates/usuarios/editar.html
    }

    // Atualizar usuário
    @PostMapping("/atualizar/{id}")
    public String atualizarUsuario(@PathVariable Long id, @Valid @ModelAttribute Usuario usuario) throws Exception {
        usuarioService.atualizarUsuario(id, usuario);
        return "redirect:/";
    }

    // Deletar usuário
    @GetMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable Long id) throws Exception {
        usuarioService.deletarUsuario(id);
        return "redirect:/usuarios";
    }
}
