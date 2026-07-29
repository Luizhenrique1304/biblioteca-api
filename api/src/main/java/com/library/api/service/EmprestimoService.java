package com.library.api.service;

import com.library.api.dto.EmprestimoRequestDto;
import com.library.api.dto.EmprestimoResponseDto;
import com.library.api.exception.RegraDeNegocioException;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.Emprestimo;
import com.library.api.model.Livro;
import com.library.api.model.Usuario;
import com.library.api.repository.EmprestimoRepository;
import com.library.api.repository.LivroRepository;
import com.library.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             LivroRepository livroRepository,
                             UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<EmprestimoResponseDto> findAll() {
        return emprestimoRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public EmprestimoResponseDto findById(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado"));

        return toResponseDto(emprestimo);
    }

    public EmprestimoResponseDto save(EmprestimoRequestDto dto) {

        Livro livro = livroRepository.findById(dto.livroId())
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // Verifica se existe exemplar disponível
        if (livro.getQuantidade() <= 0) {
            throw new RegraDeNegocioException("Não há exemplares disponíveis para empréstimo.");
        }

        // Diminui a quantidade do livro
        livro.setQuantidade(livro.getQuantidade() - 1);

        // Atualiza a disponibilidade
        if (livro.getQuantidade() == 0) {
            livro.setDisponivel(false);
        }

        livroRepository.save(livro);

        // Cria o empréstimo. dataEmprestimo e devolvido NUNCA vêm do cliente na
        // criação — todo empréstimo novo começa hoje e como não devolvido,
        // independentemente do que o dto trouxer nesses campos.
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucao(null);
        emprestimo.setDevolvido(false);

        emprestimo = emprestimoRepository.save(emprestimo);

        return toResponseDto(emprestimo);
    }

    public EmprestimoResponseDto update(Long id, EmprestimoRequestDto dto) {

        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado"));

        Livro livro = livroRepository.findById(dto.livroId())
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // Se o livro foi devolvido agora
        if (!emprestimo.isDevolvido() && Boolean.TRUE.equals(dto.devolvido())) {

            livro.setQuantidade(livro.getQuantidade() + 1);
            livro.setDisponivel(true);

            livroRepository.save(livro);
        }

        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(dto.dataEmprestimo());
        emprestimo.setDataDevolucao(dto.dataDevolucao());
        emprestimo.setDevolvido(Boolean.TRUE.equals(dto.devolvido()));

        emprestimo = emprestimoRepository.save(emprestimo);

        return toResponseDto(emprestimo);
    }

    public void delete(Long id) {
        emprestimoRepository.deleteById(id);
    }

    private EmprestimoResponseDto toResponseDto(Emprestimo emprestimo) {

        return new EmprestimoResponseDto(
                emprestimo.getId(),
                emprestimo.getLivro().getTitulo(),
                emprestimo.getUsuario().getNome(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataDevolucao(),
                emprestimo.isDevolvido()
        );
    }
}