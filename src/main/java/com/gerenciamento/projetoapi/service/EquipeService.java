package com.gerenciamento.projetoapi.service;

import com.gerenciamento.projetoapi.model.Equipe;
import com.gerenciamento.projetoapi.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    public Equipe criarEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    public List<Equipe> listarEquipes() {
        return equipeRepository.findAll();
    }

    public Equipe atualizarEquipe(Long id, Equipe equipe) {
        equipe.setId(id);
        return equipeRepository.save(equipe);
    }

    public void deletarEquipe(Long id) {
        equipeRepository.deleteById(id);
    }

    public Equipe buscarEquipe(Long id) {
        return equipeRepository.findById(id).orElse(null);
    }
}
