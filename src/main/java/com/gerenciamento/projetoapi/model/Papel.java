package com.gerenciamento.projetoapi.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "papel")
public class Papel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_papel")
    private Long id_papel;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "papel", cascade = CascadeType.ALL)
    private Set<Usuario> usuarios;

    public Papel() {
    }

    public Long getId_papel() {
        return id_papel;
    }

    public void setId_papel(Long id_papel) {
        this.id_papel = id_papel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
