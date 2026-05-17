package io.github.aprendendo_spring_data_jpa.libraryapi.controller;

import io.github.aprendendo_spring_data_jpa.libraryapi.controller.dto.AutorDTO;
import io.github.aprendendo_spring_data_jpa.libraryapi.controller.dto.ErroResposta;
import io.github.aprendendo_spring_data_jpa.libraryapi.exceptions.OperacaoNaoPermitidaExcepetions;
import io.github.aprendendo_spring_data_jpa.libraryapi.exceptions.RegistrosDuplicadosExceptions;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.Autor;
import io.github.aprendendo_spring_data_jpa.libraryapi.service.AutorService;
import io.github.aprendendo_spring_data_jpa.libraryapi.validator.AutorValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;


    @PostMapping
    public ResponseEntity<Object> salve(@RequestBody @Valid AutorDTO autor) {
        try {
            Autor autorEntity = autor.saveComDTO();
            service.salvar(autorEntity);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(autorEntity.getId()).toUri();

            return ResponseEntity.created(uri).build();
        } catch (RegistrosDuplicadosExceptions e) {
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var obterId = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(obterId);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorDTOOptional = service.obterPorId(idAutor);
            if (autorDTOOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            service.delete(autorDTOOptional.get());
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaExcepetions e) {
            var erro = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }


    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {


        List<Autor> resultado = service.pesquisaComExample(nome, nacionalidade);
        List<AutorDTO> list = resultado.stream().map(autor -> new AutorDTO(
                autor.getId(),
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade())).collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDTO) {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var autor = autorOptional.get();
            autor.setNome(autorDTO.nome());
            autor.setNacionalidade(autorDTO.nacionalidade());
            autor.setDataNascimento(autorDTO.dataNascimento());
            service.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch (RegistrosDuplicadosExceptions e) {
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }


}
