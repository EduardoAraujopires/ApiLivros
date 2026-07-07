package io.github.aprendendo_spring_data_jpa.libraryapi.validator;

import io.github.aprendendo_spring_data_jpa.libraryapi.exceptions.RegistrosDuplicadosException;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.Autor;
import io.github.aprendendo_spring_data_jpa.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor){
        if (existeAutorCadastrado(autor)){
            throw new RegistrosDuplicadosException("Autor já cadastrado!");
        }
    }
    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        if (autor.getId() == null){
            return autorEncontrado.isPresent();
        }
        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }

}
