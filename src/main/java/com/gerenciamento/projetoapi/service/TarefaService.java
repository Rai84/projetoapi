package com.gerenciamento.projetoapi.service;

import com.gerenciamento.projetoapi.model.Tarefa;
import com.gerenciamento.projetoapi.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    @Autowired
    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public Tarefa criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> buscarTarefa(Long id) {
        return tarefaRepository.findById(id);
    }

    public Tarefa atualizarTarefa(Long id, Tarefa tarefa) {
        Optional<Tarefa> tarefaExistente = tarefaRepository.findById(id);
        if (tarefaExistente.isPresent()) {
            Tarefa t = tarefaExistente.get();
            t.setDescricao(tarefa.getDescricao());
            t.setStatus(tarefa.getStatus());
            t.setResponsavel(tarefa.getResponsavel());
            return tarefaRepository.save(t);
        } else {
            throw new IllegalArgumentException("Tarefa não encontrada.");
        }
    }

    public void deletarTarefa(Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Tarefa não encontrada.");
        }
    }
}
