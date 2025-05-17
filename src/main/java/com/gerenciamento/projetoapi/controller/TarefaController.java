package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Tarefa;
import com.gerenciamento.projetoapi.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.criarTarefa(tarefa);
    }

    @GetMapping
    public List<Tarefa> listarTarefas() {
        return tarefaService.listarTarefas();
    }

    @GetMapping("/{id}")
    public Tarefa buscarTarefa(@PathVariable Long id) {
        return tarefaService.buscarTarefa(id);
    }

    @PutMapping("/{id}")
    public Tarefa atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        return tarefaService.atualizarTarefa(id, tarefa);
    }

    @DeleteMapping("/{id}")
    public void deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
    }

    // Rotas para Thymeleaf (Retornam Views)
    @Controller
    @RequestMapping("/tarefas")
    public class TarefaViewController {

        @GetMapping("/listar")
        public String listarTarefasPage() {
            return "tarefa/listar-tarefas";
        }

        @GetMapping("/novo")
        public String criarTarefaPage() {
            return "tarefa/criar-tarefa";
        }

        @GetMapping("/editar/{id}")
        public String editarTarefaPage(@PathVariable Long id) {
            return "tarefa/editar-tarefa";
        }
    }
}
