package com.library.api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmprestimoRequestDto(

        @NotNull(message = "Livro é obrigatório")
        Long livroId,

        @NotNull(message = "Usuário é obrigatório")
        Long usuarioId,

        LocalDate dataEmprestimo,
        LocalDate dataDevolucao,
        Boolean devolvido

) {
}