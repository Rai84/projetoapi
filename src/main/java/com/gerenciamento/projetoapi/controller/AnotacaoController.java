package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Anotacao;
import com.gerenciamento.projetoapi.model.Projeto;
import com.gerenciamento.projetoapi.model.Usuario;
import com.gerenciamento.projetoapi.service.AnotacaoService;
import com.gerenciamento.projetoapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/anotacoes")
public class AnotacaoController {

    @Autowired
    private AnotacaoService anotacaoService;

    @Autowired
    private UsuarioService usuarioService;

    private Usuario getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return usuarioService.buscarPorEmail(email);
    }

    @GetMapping
    public String listarAnotacoes(Model model) {
        Usuario usuario = getUsuarioLogado();
        model.addAttribute("anotacoes", anotacaoService.listarPorUsuario(usuario.getId_usuario()));
        return "anotacao/listar";
    }

    @GetMapping("/nova")
    public String novaAnotacaoForm(Model model) {
        model.addAttribute("anotacao", new Anotacao());
        return "anotacao/form";
    }

    @PostMapping("/salvar")
    public String salvarAnotacao(@ModelAttribute Anotacao anotacao) {
        Usuario usuario = getUsuarioLogado();
        anotacao.setUsuario(usuario);
        anotacaoService.salvar(anotacao);
        return "redirect:/anotacoes";
    }

    @GetMapping("/editar/{id}")
    public String editarAnotacao(@PathVariable Long id, Model model) {
        Optional<Anotacao> anotacao = anotacaoService.buscarPorId(id);
        if (anotacao.isPresent()) {
            // Verifica se a anotação pertence ao usuário logado
            Usuario usuario = getUsuarioLogado();
            if (!anotacao.get().getUsuario().getId_usuario().equals(usuario.getId_usuario())) {
                return "redirect:/anotacoes";
            }

            model.addAttribute("anotacao", anotacao.get());
            return "anotacao/form";
        }
        return "redirect:/anotacoes";
    }

    @GetMapping("/deletar/{id}")
    public String deletarAnotacao(@PathVariable Long id) {
        Optional<Anotacao> anotacao = anotacaoService.buscarPorId(id);
        if (anotacao.isPresent()) {
            Usuario usuario = getUsuarioLogado();
            if (anotacao.get().getUsuario().getId_usuario().equals(usuario.getId_usuario())) {
                anotacaoService.deletar(id);
            }
        }
        return "redirect:/anotacoes";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesAnotacao(@PathVariable Long id, Model model) {
        Optional<Anotacao> anotacao = anotacaoService.buscarPorId(id);
        if (anotacao.isPresent()) {
            model.addAttribute("anotacao", anotacao.get());
            return "anotacao/detalhes"; // nome do arquivo .html no templates
        }
        return "redirect:/anotacoes"; // ou página de erro personalizada    
    }
}   
