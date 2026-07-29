package com.library.api.dto;

import java.time.LocalDate;

public record EmprestimoRequestDto(

        Long livroId,
        Long usuarioId,
        LocalDate dataEmprestimo,
        LocalDate dataDevolucao,
        Boolean devolvido

) {
}