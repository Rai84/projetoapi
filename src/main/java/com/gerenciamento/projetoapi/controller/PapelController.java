package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Papel;
import com.gerenciamento.projetoapi.service.PapelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/papeis")
public class PapelController {

    private final PapelService papelService;

    @Autowired
    public PapelController(PapelService papelService) {
        this.papelService = papelService;
    }

    @PostMapping
    public ResponseEntity<Papel> criarPapel(@RequestBody Papel papel) {
        Papel novoPapel = papelService.criarPapel(papel);
        return ResponseEntity.ok(novoPapel);
    }

    @GetMapping
    public ResponseEntity<List<Papel>> listarPapeis() {
        return ResponseEntity.ok(papelService.listarPapeis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Papel> buscarPapel(@PathVariable Long id) {
        Optional<Papel> papel = papelService.buscarPapel(id);
        return papel.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Papel> atualizarPapel(@PathVariable Long id, @RequestBody Papel papel) {
        try {
            Papel papelAtualizado = papelService.atualizarPapel(id, papel);
            return ResponseEntity.ok(papelAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPapel(@PathVariable Long id) {
        try {
            papelService.deletarPapel(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
