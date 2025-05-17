package com.gerenciamento.projetoapi.service;

import com.gerenciamento.projetoapi.model.Projeto;
import com.gerenciamento.projetoapi.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public Projeto criarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public Projeto atualizarProjeto(Long id, Projeto projeto) {
        projeto.setId(id);
        return projetoRepository.save(projeto);
    }

    public void deletarProjeto(Long id) {
        projetoRepository.deleteById(id);
    }

    public Projeto buscarProjeto(Long id) {
        return projetoRepository.findById(id).orElse(null);
    }
}
