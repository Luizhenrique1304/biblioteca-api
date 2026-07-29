package com.library.api.controller;


import com.library.api.dto.AutorRequestDto;
import com.library.api.dto.AutorResponseDto;
import com.library.api.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblioteca/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDto>> findAll() {
        return ResponseEntity.ok(autorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AutorResponseDto> save(@RequestBody AutorRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(autorService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDto> update(@PathVariable Long id,
                                                   @RequestBody AutorRequestDto dto) {
        return ResponseEntity.ok(autorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        autorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}