package com.gerenciamento.projetoapi.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "equipe")
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipe")
    private Long id_equipe;

    @Column(nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable(name = "equipe_projeto", joinColumns = @JoinColumn(name = "id_equipe"), inverseJoinColumns = @JoinColumn(name = "id_projeto"))
    private Set<Projeto> projetos;

    public Equipe() {
    }

    public Long getId_equipe() {
        return id_equipe;
    }

    public void setId_equipe(Long id_equipe) {
        this.id_equipe = id_equipe;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(Set<Projeto> projetos) {
        this.projetos = projetos;
    }
}
