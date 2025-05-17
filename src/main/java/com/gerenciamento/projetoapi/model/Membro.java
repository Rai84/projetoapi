package com.gerenciamento.projetoapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Membro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cargo;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;
}
