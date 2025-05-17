package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Membro;
import com.gerenciamento.projetoapi.service.MembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membros")
public class MembroController {

    @Autowired
    private MembroService membroService;

    @PostMapping
    public Membro criarMembro(@RequestBody Membro membro) {
        return membroService.criarMembro(membro);
    }

    @GetMapping
    public List<Membro> listarMembros() {
        return membroService.listarMembros();
    }

    @GetMapping("/{id}")
    public Membro buscarMembro(@PathVariable Long id) {
        return membroService.buscarMembro(id);
    }

    @PutMapping("/{id}")
    public Membro atualizarMembro(@PathVariable Long id, @RequestBody Membro membro) {
        return membroService.atualizarMembro(id, membro);
    }

    @DeleteMapping("/{id}")
    public void deletarMembro(@PathVariable Long id) {
        membroService.deletarMembro(id);
    }
}
