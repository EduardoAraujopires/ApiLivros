package io.github.aprendendo_spring_data_jpa.libraryapi.controller.mappers;

import io.github.aprendendo_spring_data_jpa.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.Livro;
import io.github.aprendendo_spring_data_jpa.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);
}
