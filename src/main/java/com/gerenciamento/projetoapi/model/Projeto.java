package com.gerenciamento.projetoapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
}
