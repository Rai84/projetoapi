package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewsController {

    @Autowired
    private ProjetoService projetoService;

    // Página inicial
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Página menu
    @GetMapping("/fragments/menu")
    public String menu() {
        return "fragments/menu";
    }   

    // Modais 
    @GetMapping("/modal/{modalName}")
    public String getModal(@PathVariable String modalName) {
        return "fragments/" + modalName;
    }

    // Páginas de Usuário

    // Páginas de Projeto
    @GetMapping("/projetos/listar")
    public String listarProjetos(Model model) {
        model.addAttribute("projetos", projetoService.listarProjetos());
        return "projeto/listar-projeto";
    }

    @GetMapping("/projetos/novo")
    public String criarProjeto() {
        return "projeto/criar-projeto";
    }

    @GetMapping("/projetos/editar/{id}")
    public String editarProjeto(@PathVariable Long id, Model model) {
        model.addAttribute("projeto", projetoService.buscarProjeto(id));
        return "projeto/editar-projeto";
    }

    // Aqui você pode colocar outras rotas para views de Equipe, Membro, Tarefa,
    // Usuário, etc.
}
