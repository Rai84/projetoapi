package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Tarefa;
import com.gerenciamento.projetoapi.model.Tarefa.StatusTarefa;
import com.gerenciamento.projetoapi.service.ProjetoService;
import com.gerenciamento.projetoapi.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private ProjetoService projetoService;

    // Mostrar formulário para criar nova tarefa
    @GetMapping("/modalNovo")
    public String abrirModalNovo(@RequestParam("projetoId") Long projetoId, Model model) {
        model.addAttribute("projetoId", projetoId);
        model.addAttribute("tarefa", new Tarefa()); // adiciona um objeto vazio para o form
        return "tarefa/modalNovo";
    }

    // Criar tarefa
    @PostMapping("/novo")
    public String criarTarefa(@ModelAttribute Tarefa tarefa, @RequestParam Long projetoId) {
        Optional.ofNullable(projetoService.buscarPorId(projetoId))
                .ifPresent(projeto -> tarefa.setProjeto(projeto));
        tarefaService.criarTarefa(tarefa);
        return "redirect:/projetos/detalhes/" + projetoId;
    }

    // Mostrar formulário de edição
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Tarefa tarefa = tarefaService.buscarTarefa(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada: " + id));
        model.addAttribute("tarefa", tarefa);
        return "tarefa/editar";
    }

    // Atualizar tarefa (submissão do formulário)
    @PostMapping("/tarefas/{id}/editar")
    public String atualizarTarefa(@PathVariable Long id, @ModelAttribute Tarefa tarefaAtualizada) {
        Tarefa tarefaOriginal = tarefaService.buscarTarefa(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada: " + id));
        Long projetoId = tarefaOriginal.getProjeto().getId_projeto();

        // Manter o projeto original
        tarefaAtualizada.setProjeto(tarefaOriginal.getProjeto());

        tarefaService.atualizarTarefa(id, tarefaAtualizada);
        return "redirect:/projetos/detalhes/" + projetoId;
    }

    // Atualizar status da tarefa
    @PostMapping("/{id}/status")
    public String atualizarStatus(@PathVariable Long id, @RequestParam StatusTarefa status) {
        tarefaService.atualizarStatus(id, status);
        Optional<Tarefa> tarefaOpt = tarefaService.buscarTarefa(id);

        if (tarefaOpt.isPresent() && tarefaOpt.get().getProjeto() != null) {
            return "redirect:/projetos/detalhes/" + tarefaOpt.get().getProjeto().getId_projeto();
        }
        return "redirect:/projetos/detalhes/" + tarefaOpt.get().getProjeto().getId_projeto();
    }

    @PostMapping("/{id}/excluir")
    public String excluirTarefa(@PathVariable Long id) {
        Tarefa tarefa = tarefaService.buscarTarefa(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada: " + id));

        Long projetoId = tarefa.getProjeto().getId_projeto();
        tarefaService.deletarTarefa(id);
        return "redirect:/projetos/detalhes/" + projetoId;
    }

}
