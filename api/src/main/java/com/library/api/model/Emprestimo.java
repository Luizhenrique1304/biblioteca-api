package com.library.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "TB_EMPRESTIMOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LIVRO_ID", referencedColumnName = "id", nullable = false)
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    @Column(name = "DATA_EMPRESTIMO",  nullable = false)
    private LocalDate dataEmprestimo;

    @Column(name = "DATA_DEVOLUCAO")
    private LocalDate dataDevolucao;

    @Column(name = "DEVOLVIDO",   nullable = false)
    private boolean devolvido;

}
