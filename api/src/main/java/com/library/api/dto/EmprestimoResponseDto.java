package com.library.api.dto;

import java.time.LocalDate;
import java.util.Date;

public record EmprestimoResponseDto(

        Long id,
        String tituloLivro,
        String nomeUsuario,
        LocalDate dataEmprestimo,
        LocalDate dataDevolucao,
        Boolean devolvido

) {
}