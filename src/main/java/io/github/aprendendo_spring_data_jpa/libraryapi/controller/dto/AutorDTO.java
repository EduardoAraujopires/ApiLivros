package io.github.aprendendo_spring_data_jpa.libraryapi.controller.dto;


import io.github.aprendendo_spring_data_jpa.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatorio")
        @Size(min = 2, max = 100, message = "campo fora do tamanho padrao")
        String nome,
        @NotNull(message = "Campo obrigatorio")
        @Past(message = "Nao pode ser uma data futura.") // só aceita datas passadas.
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatorio")
        @Size(min = 3, max = 50, message = "campo fora do tamanho padrao")
        String nacionalidade
) {
}
