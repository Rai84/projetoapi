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

    @PostMapping("/salvarDetalhes")
    public String salvarAnotacaoDetalhes(@ModelAttribute Anotacao anotacao) {
        Optional<Anotacao> existente = anotacaoService.buscarPorId(anotacao.getId_anotacao());
        if (existente.isPresent()) {
            Anotacao anotacaoExistente = existente.get();

            // mantém o título original
            // atualiza só o conteúdo (supondo que seja "conteudo")
            anotacaoExistente.setConteudo(anotacao.getConteudo());

            // mantém o usuário original
            anotacaoExistente.setUsuario(anotacaoExistente.getUsuario());

            // salva a anotação atualizada
            anotacaoService.salvar(anotacaoExistente);
        }
        return "redirect:/anotacoes/detalhes/" + anotacao.getId_anotacao();
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
            Usuario usuario = getUsuarioLogado();
            if (!anotacao.get().getUsuario().getId_usuario().equals(usuario.getId_usuario())) {
                return "redirect:/anotacoes";
            }
            model.addAttribute("anotacao", anotacao.get());
            return "anotacao/detalhes";
        }
        return "redirect:/anotacoes";
    }

    

}   
