package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Equipe;
import com.gerenciamento.projetoapi.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipes")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;

    @PostMapping
    public Equipe criarEquipe(@RequestBody Equipe equipe) {
        return equipeService.criarEquipe(equipe);
    }

    @GetMapping
    public List<Equipe> listarEquipes() {
        return equipeService.listarEquipes();
    }

    @GetMapping("/{id}")
    public Equipe buscarEquipe(@PathVariable Long id) {
        return equipeService.buscarEquipe(id);
    }

    @PutMapping("/{id}")
    public Equipe atualizarEquipe(@PathVariable Long id, @RequestBody Equipe equipe) {
        return equipeService.atualizarEquipe(id, equipe);
    }

    @DeleteMapping("/{id}")
    public void deletarEquipe(@PathVariable Long id) {
        equipeService.deletarEquipe(id);
    }

    // Rotas para Thymeleaf (Retornam Views)
    @Controller
    @RequestMapping("/equipes")
    public class EquipeViewController {

        @GetMapping("/listar")
        public String listarEquipesPage() {
            return "equipe/listar-equipes";
        }

        @GetMapping("/novo")
        public String criarEquipePage() {
            return "equipe/criar-equipe";
        }

        @GetMapping("/editar/{id}")
        public String editarEquipePage(@PathVariable Long id) {
            return "equipe/editar-equipe";
        }
    }
}
