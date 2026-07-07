package io.github.aprendendo_spring_data_jpa.libraryapi.validator;

import io.github.aprendendo_spring_data_jpa.libraryapi.exceptions.RegistrosDuplicadosExceptions;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.Livro;
import io.github.aprendendo_spring_data_jpa.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository livroRepository;

    public void validar(Livro livro){
        if (existeLivroComIsbn(livro)){
            throw new RegistrosDuplicadosExceptions("ISBN já cadastrado!");
        }
    }

    public boolean existeLivroComIsbn ( Livro livro){

        Optional<Livro> encontrado = livroRepository.findByIsbn(livro.getIsbn());

        if (livro.getId() == null){
            return encontrado.isPresent();
        }

        return encontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id-> !id.equals(livro.getId()));
    }
}
