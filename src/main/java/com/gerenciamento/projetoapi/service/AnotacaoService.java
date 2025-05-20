package com.gerenciamento.projetoapi.service;

import com.gerenciamento.projetoapi.model.Anotacao;
import com.gerenciamento.projetoapi.repository.AnotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnotacaoService {

    @Autowired
    private AnotacaoRepository anotacaoRepository;

    public List<Anotacao> listarTodas() {
        return anotacaoRepository.findAll();
    }

    public Optional<Anotacao> buscarPorId(Long id) {
        return anotacaoRepository.findById(id);
    }

    public Anotacao salvar(Anotacao anotacao) {
        return anotacaoRepository.save(anotacao);
    }

    public void deletar(Long id) {
        anotacaoRepository.deleteById(id);
    }

    public List<Anotacao> listarPorUsuario(Long id_Usuario) {
        return anotacaoRepository.findByUsuarioId(id_Usuario);
    }    
    
}
