package com.gerenciamento.projetoapi.service;

import com.gerenciamento.projetoapi.model.Nota;
import com.gerenciamento.projetoapi.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    public Optional<Nota> buscarNotaPorId(Long id) {
        return notaRepository.findById(id);
    }

    public Optional<Nota> buscarNotaPorUsuarioId(Long idUsuario) {
        return notaRepository.findByUsuarioId(idUsuario);
    }    

    public Nota atualizarNota(Long idNota, String novoConteudo) {
        Nota nota = notaRepository.findById(idNota)
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada com ID: " + idNota));
        nota.setConteudo(novoConteudo);
        return notaRepository.save(nota);
    }
}
