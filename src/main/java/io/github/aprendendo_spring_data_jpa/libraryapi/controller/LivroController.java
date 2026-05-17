package io.github.aprendendo_spring_data_jpa.libraryapi.controller;

import io.github.aprendendo_spring_data_jpa.libraryapi.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;
}
