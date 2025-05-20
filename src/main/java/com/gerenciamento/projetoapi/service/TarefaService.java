package com.gerenciamento.projetoapi.service;

import com.gerenciamento.projetoapi.model.Tarefa;
import com.gerenciamento.projetoapi.model.Tarefa.StatusTarefa;
import com.gerenciamento.projetoapi.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public Tarefa criarTarefa(Tarefa tarefa) {
        // Poderia validar dados obrigatórios aqui, se quiser
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> buscarTarefa(Long id) {
        return tarefaRepository.findById(id);
    }

    

    public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada com id: " + id));

        // Atualiza os campos necessários
        tarefa.setNome(tarefaAtualizada.getNome());
        tarefa.setDescricao(tarefaAtualizada.getDescricao());
        tarefa.setStatus(tarefaAtualizada.getStatus());

        // Só atualiza projeto e responsável se vierem preenchidos para evitar
        // sobrescrever com null
        if (tarefaAtualizada.getProjeto() != null) {
            tarefa.setProjeto(tarefaAtualizada.getProjeto());
        }
        if (tarefaAtualizada.getResponsavel() != null) {
            tarefa.setResponsavel(tarefaAtualizada.getResponsavel());
        }

        return tarefaRepository.save(tarefa);
    }

    public void deletarTarefa(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new IllegalArgumentException("Tarefa não encontrada com id: " + id);
        }
        tarefaRepository.deleteById(id);
    }

    public Tarefa atualizarStatus(Long id, StatusTarefa novoStatus) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada com id: " + id));

        tarefa.setStatus(novoStatus);
        return tarefaRepository.save(tarefa);
    }
}
