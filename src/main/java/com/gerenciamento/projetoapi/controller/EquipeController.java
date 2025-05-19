package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Equipe;
import com.gerenciamento.projetoapi.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipes")
public class EquipeController {

    private final EquipeService equipeService;

    @Autowired
    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping
    public ResponseEntity<Equipe> criarEquipe(@RequestBody Equipe equipe) {
        Equipe novaEquipe = equipeService.criarEquipe(equipe);
        return ResponseEntity.ok(novaEquipe);
    }

    @GetMapping
    public ResponseEntity<List<Equipe>> listarEquipes() {
        return ResponseEntity.ok(equipeService.listarEquipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> buscarEquipe(@PathVariable Long id) {
        Optional<Equipe> equipe = equipeService.buscarEquipe(id);
        return equipe.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipe> atualizarEquipe(@PathVariable Long id, @RequestBody Equipe equipe) {
        try {
            Equipe equipeAtualizada = equipeService.atualizarEquipe(id, equipe);
            return ResponseEntity.ok(equipeAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEquipe(@PathVariable Long id) {
        try {
            equipeService.deletarEquipe(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
