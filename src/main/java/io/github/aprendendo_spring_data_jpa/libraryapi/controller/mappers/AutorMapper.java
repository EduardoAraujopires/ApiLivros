package io.github.aprendendo_spring_data_jpa.libraryapi.controller.mappers;

import io.github.aprendendo_spring_data_jpa.libraryapi.controller.dto.AutorDTO;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
