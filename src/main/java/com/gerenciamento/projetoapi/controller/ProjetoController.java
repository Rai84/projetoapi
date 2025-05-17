package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Projeto;
import com.gerenciamento.projetoapi.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    public Projeto criarProjeto(@RequestBody Projeto projeto) {
        return projetoService.criarProjeto(projeto);
    }

    @GetMapping
    public List<Projeto> listarProjetos() {
        return projetoService.listarProjetos();
    }

    @GetMapping("/{id}")
    public Projeto buscarProjeto(@PathVariable Long id) {
        return projetoService.buscarProjeto(id);
    }

    @PutMapping("/{id}")
    public Projeto atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projeto) {
        return projetoService.atualizarProjeto(id, projeto);
    }

    @DeleteMapping("/{id}")
    public void deletarProjeto(@PathVariable Long id) {
        projetoService.deletarProjeto(id);
    }
}
