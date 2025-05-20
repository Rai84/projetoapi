package com.gerenciamento.projetoapi.service;

import com.gerenciamento.projetoapi.model.Projeto;
import com.gerenciamento.projetoapi.repository.ProjetoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // Criar projeto
    public Projeto criarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    // Listar com paginação
    public Page<Projeto> listarProjetos(Pageable pageable) {
        return projetoRepository.findAll(pageable);
    }

    // Buscar por ID
    public Optional<Projeto> buscarProjeto(Long id) {
        return projetoRepository.findById(id);
    }

    // Atualizar projeto
    public Projeto atualizarProjeto(Long id, Projeto projeto) {
        return projetoRepository.findById(id)
                .map(p -> {
                    p.setNome(projeto.getNome());
                    p.setDescricao(projeto.getDescricao());
                    return projetoRepository.save(p);
                })
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado."));
    }

    // Deletar projeto
    public void deletarProjeto(Long id) {
        if (projetoRepository.existsById(id)) {
            projetoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Projeto não encontrado.");
        }
    }

    public List<Projeto> listarProjetosPorUsuario(Long idUsuario) {
        return projetoRepository.findByUsuarioIdUsuario(idUsuario);
    }    

    public Projeto buscarPorId(Long id) {
        return projetoRepository.findById(id).orElse(null);
    }    
    
}
