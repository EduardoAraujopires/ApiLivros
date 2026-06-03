package io.github.aprendendo_spring_data_jpa.libraryapi.service;

import io.github.aprendendo_spring_data_jpa.libraryapi.model.GeneroLivro;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.Livro;
import io.github.aprendendo_spring_data_jpa.libraryapi.repository.LivroRepository;
import io.github.aprendendo_spring_data_jpa.libraryapi.repository.specs.LivroSpecs;
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

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void delete(UUID id) {
        livroRepository.deleteById(id);
    }

    public List<Livro> pesquisa(
            String isbn,
            String titulo,
            String nome,
            GeneroLivro genero, Integer anoPublicacao) {
        Specification<Livro> specs = Specification.where((root, query, cb) ->
          cb.conjunction());

        if(isbn != null){
             specs = specs.and(isbnEqual(isbn));
        }

        if (genero != null){
            specs = specs.and(generoEqual(genero));
        }

        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        return livroRepository.findAll(specs);
    }
}