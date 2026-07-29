package com.library.api.service;

import com.library.api.dto.UsuarioRequestDto;
import com.library.api.dto.UsuarioResponseDto;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.Usuario;
import com.library.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioResponseDto> findAll() {

        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> new UsuarioResponseDto(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()))
                .toList();
    }

    public UsuarioResponseDto findById(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail());
    }

    public UsuarioResponseDto save(UsuarioRequestDto dto) {

        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());

        usuario = usuarioRepository.save(usuario);

        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail());
    }

    public UsuarioResponseDto update(Long id, UsuarioRequestDto dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());

        usuario = usuarioRepository.save(usuario);

        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail());
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}

