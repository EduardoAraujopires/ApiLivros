package io.github.aprendendo_spring_data_jpa.libraryapi.exceptions;

public class RegistrosDuplicadosException extends RuntimeException{
    public RegistrosDuplicadosException(String message) {
        super(message);
    }
}
