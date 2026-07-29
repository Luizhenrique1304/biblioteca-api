package com.library.api.controller;

import com.library.api.dto.LivroRequestDto;
import com.library.api.dto.LivroResponseDto;
import com.library.api.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblioteca/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDto>> findAll() {
        return ResponseEntity.ok(livroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.findById(id));
    }

    @PostMapping
    public ResponseEntity<LivroResponseDto> save(@Valid @RequestBody LivroRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(livroService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDto> update(@PathVariable Long id,
                                                   @Valid @RequestBody LivroRequestDto dto) {
        return ResponseEntity.ok(livroService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}