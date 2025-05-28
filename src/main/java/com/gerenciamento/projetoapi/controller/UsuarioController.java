package com.gerenciamento.projetoapi.controller;

import com.gerenciamento.projetoapi.model.Usuario;
import com.gerenciamento.projetoapi.service.UsuarioService;
import com.gerenciamento.projetoapi.service.details.UsuarioDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Listar usuários com paginação
    @GetMapping
    public String listarUsuarios(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Usuario> usuariosPage = usuarioService.listarUsuarios(PageRequest.of(page, 10));
        model.addAttribute("usuarios", usuariosPage.getContent());
        model.addAttribute("usuariosPage", usuariosPage);
        return "usuario/lista"; // templates/usuarios/lista.html
    }

    // Mostrar formulário para novo usuário
    @GetMapping("/novo")
    public String mostrarFormularioCriarUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/novo"; // templates/usuarios/novo.html
    }

    // Salvar novo usuário
    @PostMapping("/salvar")
    public String salvarUsuario(@Valid @ModelAttribute Usuario usuario) {
        usuarioService.salvarUsuario(usuario);
        return "redirect:/usuarios";
    }

    // Mostrar formulário para editar usuário
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarUsuario(@PathVariable Long id, Model model) throws Exception {
        Usuario usuario = usuarioService.buscarUsuario(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
        model.addAttribute("usuario", usuario);
        return "usuario/editar"; // templates/usuarios/editar.html
    }

    // Atualizar usuário
    @PostMapping("/atualizar/{id}")
    public String atualizarUsuario(@PathVariable Long id, @Valid @ModelAttribute Usuario usuario, Authentication authentication, HttpServletRequest request) throws Exception {
        usuarioService.atualizarUsuario(id, usuario);
    
        // Atualizar o objeto UsuarioDetails na sessão
        if (authentication != null && authentication.getPrincipal() instanceof UsuarioDetails) {
            UsuarioDetails usuarioDetails = (UsuarioDetails) authentication.getPrincipal();
        
            // Atualizar os dados do usuário no UsuarioDetails (ou recarregar do banco)
            Usuario usuarioAtualizado = usuarioService.buscarPorEmail(usuarioDetails.getEmail());
            
            UsuarioDetails novoUsuarioDetails = new UsuarioDetails(usuarioAtualizado);
        
            // Crie um novo token de autenticação com o novo principal
            Authentication novoAuth = new UsernamePasswordAuthenticationToken(
                novoUsuarioDetails,
                authentication.getCredentials(),
                novoUsuarioDetails.getAuthorities()
            );
        
            // Atualize o contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(novoAuth);
        
            // Opcional: atualizar também a sessão HTTP (se necessário)
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            }
        }
    
        return "redirect:/";
    }



    // Deletar usuário
    @GetMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable Long id) throws Exception {
        usuarioService.deletarUsuario(id);
        return "redirect:/usuarios";
    }
}
