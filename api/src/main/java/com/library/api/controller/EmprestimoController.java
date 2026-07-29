package com.library.api.controller;

import com.library.api.dto.EmprestimoRequestDto;
import com.library.api.dto.EmprestimoResponseDto;
import com.library.api.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblioteca/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoResponseDto>> findAll() {
        return ResponseEntity.ok(emprestimoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponseDto> save(@Valid @RequestBody EmprestimoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(emprestimoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDto> update(@PathVariable Long id,
                                                        @Valid @RequestBody EmprestimoRequestDto dto) {
        return ResponseEntity.ok(emprestimoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        emprestimoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}