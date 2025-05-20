package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Nota;
import com.gerenciamento.projetoapi.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    // Visualizar a nota de um usuário
    @GetMapping("/usuario/{idUsuario}")
    public String visualizarNotaUsuario(@PathVariable Long idUsuario, Model model) {
        Nota nota = notaService.buscarNotaPorUsuarioId(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada para o usuário: " + idUsuario));
        model.addAttribute("nota", nota);
        return "nota/visualizar"; // templates/nota/visualizar.html
    }

    // Mostrar formulário para editar a nota
    @GetMapping("/editar/{idNota}")
    public String editarNota(@PathVariable Long idNota, Model model) {
        Nota nota = notaService.buscarNotaPorId(idNota)
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada: " + idNota));
        model.addAttribute("nota", nota);
        return "nota/editar"; // templates/nota/editar.html
    }

    // Atualizar a nota
    @PostMapping("/atualizar/{idNota}")
    public String atualizarNota(@PathVariable Long idNota, @RequestParam String conteudo) {
        notaService.atualizarNota(idNota, conteudo);
        return "redirect:/notas/editar/" + idNota + "?atualizado=true";
    }
}
