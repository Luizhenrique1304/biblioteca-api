package com.library.api.dto;

public record UsuarioResponseDto (
    Long id,
    String nome,
    String email
) {
}
