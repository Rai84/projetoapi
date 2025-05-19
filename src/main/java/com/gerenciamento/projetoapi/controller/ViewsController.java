package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Usuario;
import com.gerenciamento.projetoapi.service.ProjetoService;
import com.gerenciamento.projetoapi.service.UsuarioService;
import com.gerenciamento.projetoapi.service.details.UsuarioDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewsController {

    @Autowired
    private UsuarioService usuarioService;

    // Página inicial
    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        if (authentication != null && authentication.getPrincipal() instanceof UsuarioDetails) {
            UsuarioDetails usuario = (UsuarioDetails) authentication.getPrincipal();
            model.addAttribute("usuario", usuario);
            model.addAttribute("username", usuario.getNome());
            model.addAttribute("email", usuario.getEmail());
        }
        return "index";
    }

    // Página menu
    @GetMapping("/fragments/menu")
    public String menu() {
        return "fragments/menu";
    }   

    @GetMapping("/modal/{modalName}")
    public String getModal(@PathVariable String modalName, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Busca o usuário pelo username (assumindo que username é email ou nome único)
        Optional<Usuario> usuarioOpt = usuarioService.listarUsuarios(Pageable.unpaged())
                .stream()
                .filter(u -> u.getEmail().equals(username) || u.getNome().equals(username))
                .findFirst();

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            model.addAttribute("username", usuario.getNome());
            model.addAttribute("idUsuario", usuario.getId_usuario());
        } else {
            // Caso não encontre, pode colocar valores padrão ou tratar erro
            model.addAttribute("username", username);
            model.addAttribute("idUsuario", 0);
        }

        return "fragments/" + modalName;
    }



    // Página de Login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

}