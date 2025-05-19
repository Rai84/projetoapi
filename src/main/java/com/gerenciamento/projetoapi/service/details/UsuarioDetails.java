package com.gerenciamento.projetoapi.service.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gerenciamento.projetoapi.model.Usuario;

import java.util.Collection;


public class UsuarioDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String nome;

    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioDetails(Usuario user) {
        this.id = user.getId_usuario();
        this.username = user.getEmail(); // E-mail como username
        this.password = user.getSenha();
        this.nome = user.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return username;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
