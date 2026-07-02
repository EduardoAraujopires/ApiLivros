package io.github.aprendendo_spring_data_jpa.libraryapi.controller;

import io.github.aprendendo_spring_data_jpa.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.aprendendo_spring_data_jpa.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.aprendendo_spring_data_jpa.libraryapi.controller.mappers.LivroMapper;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.GeneroLivro;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.Livro;
import io.github.aprendendo_spring_data_jpa.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);
        var uri = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterPorId
            (@PathVariable("id") String id){

        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") String id ){
      return service.obterPorId(UUID.fromString(id))
              .map(livro -> {
                  service.delete(livro.getId());
                  return ResponseEntity.noContent().build();
              }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn" ,  required = false)
            String isbn,

            @RequestParam(value = "titulo" ,  required = false)
            String titulo,

            @RequestParam(value = "nome" ,  required = false)
            String nome,

            @RequestParam(value = "genero" ,  required = false)
            GeneroLivro genero,

            @RequestParam(value = "ano-publicacao" ,  required = false)
            Integer anoPublicacao) {


        var resultado = service.pesquisa(isbn, titulo, nome, genero, anoPublicacao);
        var lista = resultado
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @Valid @RequestBody CadastroLivroDTO dto){

        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {

                    Livro entidadeAux = mapper.toEntity(dto);

                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setGenero(entidadeAux.getGenero());
                    livro.setIsbn(entidadeAux.getIsbn());
                    livro.setPreco(entidadeAux.getPreco());
                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setAutor(entidadeAux.getAutor());
                    service.atualizar(livro);

                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
