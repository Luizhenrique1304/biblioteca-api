package com.library.api.dto;

import jakarta.validation.constraints.NotBlank;

public record AutorRequestDto (

    @NotBlank(message = "Nome é obrigatório")
    String nome
) {
}
