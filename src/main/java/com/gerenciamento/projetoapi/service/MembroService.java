package com.gerenciamento.projetoapi.service;

import com.gerenciamento.projetoapi.model.Membro;
import com.gerenciamento.projetoapi.repository.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MembroService {

    @Autowired
    private MembroRepository membroRepository;

    public Membro criarMembro(Membro membro) {
        return membroRepository.save(membro);
    }

    public List<Membro> listarMembros() {
        return membroRepository.findAll();
    }

    public Membro atualizarMembro(Long id, Membro membro) {
        membro.setId(id);
        return membroRepository.save(membro);
    }

    public void deletarMembro(Long id) {
        membroRepository.deleteById(id);
    }

    public Membro buscarMembro(Long id) {
        return membroRepository.findById(id).orElse(null);
    }
}
