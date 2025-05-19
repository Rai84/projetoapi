package com.gerenciamento.projetoapi.service;

import com.gerenciamento.projetoapi.model.Papel;
import com.gerenciamento.projetoapi.repository.PapelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PapelService {

    private final PapelRepository papelRepository;

    @Autowired
    public PapelService(PapelRepository papelRepository) {
        this.papelRepository = papelRepository;
    }

    public Papel criarPapel(Papel papel) {
        return papelRepository.save(papel);
    }

    public List<Papel> listarPapeis() {
        return papelRepository.findAll();
    }

    public Optional<Papel> buscarPapel(Long id) {
        return papelRepository.findById(id);
    }

    public Papel atualizarPapel(Long id, Papel papel) {
        Optional<Papel> papelExistente = papelRepository.findById(id);
        if (papelExistente.isPresent()) {
            Papel p = papelExistente.get();
            p.setNome(papel.getNome());
            p.setDescricao(papel.getDescricao());
            return papelRepository.save(p);
        } else {
            throw new IllegalArgumentException("Papel não encontrado.");
        }
    }

    public void deletarPapel(Long id) {
        if (papelRepository.existsById(id)) {
            papelRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Papel não encontrado.");
        }
    }
}
