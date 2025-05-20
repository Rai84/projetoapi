package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Anotacao;
// import com.gerenciamento.projetoapi.model.Anotacao;
import com.gerenciamento.projetoapi.model.Projeto;
import com.gerenciamento.projetoapi.model.Usuario;
import com.gerenciamento.projetoapi.service.ProjetoService;
import com.gerenciamento.projetoapi.service.AnotacaoService;
import com.gerenciamento.projetoapi.service.UsuarioService;
import com.gerenciamento.projetoapi.service.details.UsuarioDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class ViewsController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProjetoService projetoService; // injetando serviço de projetos

    @Autowired
    private AnotacaoService anotacaoService; // injetando serviço de anotações

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

    // Página menu - aqui adicionamos os projetos do usuário logado
    @GetMapping("/fragments/menu")
    public String menu(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<Usuario> usuarioOpt = usuarioService.listarUsuarios(Pageable.unpaged())
                .stream()
                .filter(u -> u.getEmail().equals(username) || u.getNome().equals(username))
                .findFirst();

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            model.addAttribute("username", usuario.getNome());
            model.addAttribute("idUsuario", usuario.getId_usuario());

            // Buscar projetos do usuário logado e adicionar no model
            List<Projeto> projetos = projetoService.listarProjetosPorUsuario(usuario.getId_usuario());
            model.addAttribute("projetos", projetos);

            List<Anotacao> anotacoes = anotacaoService.listarPorUsuario(usuario.getId_usuario());
            model.addAttribute("anotacoes", anotacoes);
        } else {
            model.addAttribute("username", username);
            model.addAttribute("idUsuario", 0);
            model.addAttribute("projetos", List.of()); // lista vazia para evitar erro
        }

        return "fragments/menu";
    }

    @GetMapping("/modal/{modalName}")
    public String getModal(@PathVariable String modalName, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<Usuario> usuarioOpt = usuarioService.listarUsuarios(Pageable.unpaged())
                .stream()
                .filter(u -> u.getEmail().equals(username) || u.getNome().equals(username))
                .findFirst();

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            model.addAttribute("username", usuario.getNome());
            model.addAttribute("idUsuario", usuario.getId_usuario());
        } else {
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
