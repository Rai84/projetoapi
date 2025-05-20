package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Projeto;
import com.gerenciamento.projetoapi.model.Usuario;
import com.gerenciamento.projetoapi.service.ProjetoService;
import com.gerenciamento.projetoapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;
    private final UsuarioService usuarioService;

    @Autowired
    public ProjetoController(ProjetoService projetoService, UsuarioService usuarioService) {
        this.projetoService = projetoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarProjetos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuarioLogado = usuarioService.buscarPorEmail(userDetails.getUsername());
        List<Projeto> projetos = projetoService.listarProjetosPorUsuario(usuarioLogado.getId_usuario());
        model.addAttribute("projetos", projetos);
        return "projeto/lista";
    }

    @GetMapping("/novo")
    public String mostrarFormularioCriarProjeto(Model model) {
        model.addAttribute("projeto", new Projeto());
        return "projeto/novo";
    }

    @PostMapping("/salvar")
    public String salvarProjeto(@ModelAttribute Projeto projeto, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuarioLogado = usuarioService.buscarPorEmail(userDetails.getUsername());
        projeto.setUsuario(usuarioLogado);
        projetoService.criarProjeto(projeto);
        return "redirect:/projetos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProjeto(@PathVariable Long id, Model model) {
        Projeto projeto = projetoService.buscarProjeto(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado com id: " + id));
        model.addAttribute("projeto", projeto);
        return "projeto/editar";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarProjeto(@PathVariable Long id, @ModelAttribute Projeto projetoAtualizado) {
        projetoService.atualizarProjeto(id, projetoAtualizado);
        return "redirect:/projetos";
    }

    @GetMapping("/deletar/{id}")
    public String deletarProjeto(@PathVariable Long id) {
        projetoService.deletarProjeto(id);
        return "redirect:/projetos";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesProjeto(@PathVariable Long id, Model model) {
        Projeto projeto = projetoService.buscarPorId(id);
        if (projeto == null) {
            return "redirect:/projetos"; // ou página de erro personalizada
        }
        model.addAttribute("projeto", projeto);
        return "projeto/detalhes"; // nome do arquivo .html no templates
    }

}
