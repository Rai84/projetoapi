package com.gerenciamento.projetoapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private boolean concluida;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;
}
