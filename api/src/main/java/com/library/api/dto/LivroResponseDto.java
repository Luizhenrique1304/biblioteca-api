package com.library.api.dto;

import com.library.api.model.Livro;

public record LivroResponseDto(
        Long id,
        String titulo,
        Integer anoPublicacao,
        Integer quantidade,
        Boolean disponivel,
        String nomeAutor
) {

    public static LivroResponseDto fromEntity(Livro livro) {
        return new LivroResponseDto(
                livro.getId(),
                livro.getTitulo(),
                livro.getAnoPublicacao(),
                livro.getQuantidade(),
                livro.getDisponivel(),
                livro.getAutor().getNome()
        );
    }
}
