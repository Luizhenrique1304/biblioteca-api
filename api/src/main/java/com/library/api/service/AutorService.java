package com.library.api.service;

import com.library.api.dto.AutorRequestDto;
import com.library.api.dto.AutorResponseDto;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.Autor;
import com.library.api.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<AutorResponseDto> findAll() {
        return autorRepository.findAll()
                .stream()
                .map(autor -> new AutorResponseDto(
                        autor.getId(),
                        autor.getNome()))
                .toList();
    }

    public AutorResponseDto findById(Long id) {

        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado"));

        return new AutorResponseDto(
                autor.getId(),
                autor.getNome());
    }

    public AutorResponseDto save(AutorRequestDto dto) {

        Autor autor = new Autor();
        autor.setNome(dto.nome());

        autor = autorRepository.save(autor);

        return new AutorResponseDto(
                autor.getId(),
                autor.getNome());
    }

    public AutorResponseDto update(Long id, AutorRequestDto dto) {

        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado"));

        autor.setNome(dto.nome());

        autor = autorRepository.save(autor);

        return new AutorResponseDto(
                autor.getId(),
                autor.getNome());
    }

    public void delete(Long id) {
        autorRepository.deleteById(id);
    }
}