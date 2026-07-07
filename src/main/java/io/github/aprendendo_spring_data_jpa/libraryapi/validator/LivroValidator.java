package io.github.aprendendo_spring_data_jpa.libraryapi.validator;

import io.github.aprendendo_spring_data_jpa.libraryapi.exceptions.CampoInvalidoException;
import io.github.aprendendo_spring_data_jpa.libraryapi.exceptions.RegistrosDuplicadosException;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.Livro;
import io.github.aprendendo_spring_data_jpa.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository livroRepository;
    private final static int ANO_EXIGENCIA_PRECO = 2020;

    public void validar(Livro livro){
        if (existeLivroComIsbn(livro)){
            throw new RegistrosDuplicadosException("ISBN já cadastrado!");
        }

        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("Preco", "Para Livros com ano de publicação a partir de 2020. O preco é obrigatorio ");
        }
    }

    public boolean isPrecoObrigatorioNulo(Livro livro){
        return livro.getPreco() == null
                && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
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
