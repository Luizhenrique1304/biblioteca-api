package com.library.api.service;

import com.library.api.dto.LivroRequestDto;
import com.library.api.dto.LivroResponseDto;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.Autor;
import com.library.api.model.Livro;
import com.library.api.repository.AutorRepository;
import com.library.api.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public List<LivroResponseDto> getAllLivros() {
        return livroRepository.findAll()
                .stream()
                .map(LivroResponseDto::fromEntity)
                .toList();
    }

    public LivroResponseDto findById(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com id: " + id));
        return LivroResponseDto.fromEntity(livro);
    }

    public LivroResponseDto save(LivroRequestDto dto) {
        Livro livro = new Livro();
        preencherLivro(livro, dto);
        return LivroResponseDto.fromEntity(livroRepository.save(livro));
    }

    public LivroResponseDto update(Long id, LivroRequestDto dto) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com id: " + id));

        preencherLivro(livro, dto);
        return LivroResponseDto.fromEntity(livroRepository.save(livro));
    }

    public void delete(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Livro não encontrado com id: " + id);
        }
        livroRepository.deleteById(id);
    }

    // Busca o autor pelo nome; se não existir, cria um novo automaticamente.
    // Se preferir exigir que o autor já esteja cadastrado, troque orElseGet por
    // orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado: " + nomeAutor))
    private Autor resolverAutor(String nomeAutor) {
        return autorRepository.findByNome(nomeAutor)
                .orElseGet(() -> {
                    Autor novoAutor = new Autor();
                    novoAutor.setNome(nomeAutor);
                    return autorRepository.save(novoAutor);
                });
    }

    private void preencherLivro(Livro livro, LivroRequestDto dto) {
        Autor autor = resolverAutor(dto.nomeAutor());

        livro.setTitulo(dto.titulo());
        livro.setAnoPublicacao(dto.anoPublicacao());
        livro.setQuantidade(dto.quantidade());
        livro.setDisponivel(dto.quantidade() != null && dto.quantidade() > 0);
        livro.setAutor(autor);
    }

    public List<LivroResponseDto> findAll() {
        return livroRepository.findAll()
                .stream()
                .map(LivroResponseDto::fromEntity)
                .toList();
    }
}