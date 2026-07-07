package io.github.aprendendo_spring_data_jpa.libraryapi.service;

import io.github.aprendendo_spring_data_jpa.libraryapi.model.GeneroLivro;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.Livro;
import io.github.aprendendo_spring_data_jpa.libraryapi.repository.LivroRepository;
import io.github.aprendendo_spring_data_jpa.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.aprendendo_spring_data_jpa.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator validator;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void delete(UUID id) {
        livroRepository.deleteById(id);
    }

    public void atualizar(Livro livro) {

        if (livro.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessario que o livro esteje cadastrado na base de dados.");

        }
        validator.validar(livro);
        livroRepository.save(livro);
    }

    public List<Livro> pesquisa(String isbn, String titulo, String nome, GeneroLivro genero, Integer anoPublicacao) {
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (isbn != null) {
            specs = specs.and(isbnEqual(isbn));
        }

        if (genero != null) {
            specs = specs.and(generoEqual(genero));
        }

        if (titulo != null) {
            specs = specs.and(tituloLike(titulo));
        }
        if (anoPublicacao != null) {
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }
        if (nome != null) {
            specs = specs.and(nomeAutorLike(nome));
        }
        return livroRepository.findAll(specs);
    }
}