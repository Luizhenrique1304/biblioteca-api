package com.library.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroRequestDto(

        @NotBlank(message = "Título é obrigatório")
        String titulo,

        @NotNull(message = "Ano de publicação é obrigatório")
        Integer anoPublicacao,

        @NotNull(message = "Quantidade é obrigatória")
        Integer quantidade,

        @NotBlank(message = "Nome do autor é obrigatório")
        String nomeAutor
) {
}
