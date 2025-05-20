package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Tarefa;
import com.gerenciamento.projetoapi.model.Tarefa.StatusTarefa;
import com.gerenciamento.projetoapi.service.ProjetoService;
import com.gerenciamento.projetoapi.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public String editarTarefaForm(@PathVariable Long id, Model model) {
        Optional<Tarefa> tarefaOpt = tarefaService.buscarTarefa(id);
        if (tarefaOpt.isEmpty()) {
            model.addAttribute("erro", "Tarefa não encontrada");
            return "erro"; // página de erro customizada
        }
        Tarefa tarefa = tarefaOpt.get();
        model.addAttribute("tarefa", tarefa);
        if (tarefa.getProjeto() != null) {
            model.addAttribute("projetoId", tarefa.getProjeto().getId_projeto());
            model.addAttribute("projetoNome", tarefa.getProjeto().getNome());
            model.addAttribute("tarefa", tarefa);
            model.addAttribute("projetoId", tarefa.getProjeto().getId_projeto()); // ou onde estiver armazenado
            model.addAttribute("statusOptions", List.of("PENDENTE", "EM_ANDAMENTO", "CONCLUÍDA"));

        }
        model.addAttribute("statusOptions", StatusTarefa.values());
        return "tarefa/editar";
    }

    // Atualizar tarefa (submissão do formulário)
    @PostMapping("/atualizar/{id}")
    public String atualizarTarefa(@PathVariable Long id, @ModelAttribute Tarefa tarefaAtualizada) {
        System.out.println("NOME ATUALIZADO: " + tarefaAtualizada.getNome());
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
