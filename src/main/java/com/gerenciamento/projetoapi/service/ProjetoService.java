package com.gerenciamento.projetoapi.service;

import com.gerenciamento.projetoapi.model.Projeto;
import com.gerenciamento.projetoapi.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    @Autowired
    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public Projeto criarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public Optional<Projeto> buscarProjeto(Long id) {
        return projetoRepository.findById(id);
    }

    public Projeto atualizarProjeto(Long id, Projeto projeto) {
        Optional<Projeto> projetoExistente = projetoRepository.findById(id);
        if (projetoExistente.isPresent()) {
            Projeto p = projetoExistente.get();
            p.setNome(projeto.getNome());
            p.setDescricao(projeto.getDescricao());
            p.setEquipe(projeto.getEquipe());
            return projetoRepository.save(p);
        } else {
            throw new IllegalArgumentException("Projeto não encontrado.");
        }
    }

    public void deletarProjeto(Long id) {
        if (projetoRepository.existsById(id)) {
            projetoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Projeto não encontrado.");
        }
    }
}
