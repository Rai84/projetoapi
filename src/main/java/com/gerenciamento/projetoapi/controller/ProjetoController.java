package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Projeto;
import com.gerenciamento.projetoapi.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    @Autowired
    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @PostMapping
    public ResponseEntity<Projeto> criarProjeto(@RequestBody Projeto projeto) {
        Projeto novoProjeto = projetoService.criarProjeto(projeto);
        return ResponseEntity.ok(novoProjeto);
    }

    @GetMapping
    public ResponseEntity<List<Projeto>> listarProjetos() {
        return ResponseEntity.ok(projetoService.listarProjetos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarProjeto(@PathVariable Long id) {
        Optional<Projeto> projeto = projetoService.buscarProjeto(id);
        return projeto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Projeto> atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projeto) {
        try {
            Projeto projetoAtualizado = projetoService.atualizarProjeto(id, projeto);
            return ResponseEntity.ok(projetoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProjeto(@PathVariable Long id) {
        try {
            projetoService.deletarProjeto(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
