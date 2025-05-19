package com.gerenciamento.projetoapi.service;

import com.gerenciamento.projetoapi.model.Equipe;
import com.gerenciamento.projetoapi.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;

    @Autowired
    public EquipeService(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    public Equipe criarEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    public List<Equipe> listarEquipes() {
        return equipeRepository.findAll();
    }

    public Optional<Equipe> buscarEquipe(Long id) {
        return equipeRepository.findById(id);
    }

    public Equipe atualizarEquipe(Long id, Equipe equipe) {
        Optional<Equipe> equipeExistente = equipeRepository.findById(id);
        if (equipeExistente.isPresent()) {
            Equipe e = equipeExistente.get();
            e.setNome(equipe.getNome());
            e.setProjetos(equipe.getProjetos());
            return equipeRepository.save(e);
        } else {
            throw new IllegalArgumentException("Equipe não encontrada.");
        }
    }

    public void deletarEquipe(Long id) {
        if (equipeRepository.existsById(id)) {
            equipeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Equipe não encontrada.");
        }
    }
}