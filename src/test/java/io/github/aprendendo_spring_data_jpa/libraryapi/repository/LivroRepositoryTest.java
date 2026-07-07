package io.github.aprendendo_spring_data_jpa.libraryapi.repository;

import io.github.aprendendo_spring_data_jpa.libraryapi.model.Autor;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.GeneroLivro;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvar(){
       Livro livro = new Livro();
       livro.setTitulo("Ciencia ");
       livro.setIsbn("45981-2141");
       livro.setGenero(GeneroLivro.CIENCIA);
       livro.setPreco(BigDecimal.valueOf(170));
       livro.setDataPublicacao(LocalDate.of(2019, 10, 2));
        UUID idAutor = UUID.fromString("84e49399-8dae-4502-9e3c-ca2e75561888");
        Autor autor = autorRepository.findById(idAutor).orElse(null);
        livro.setAutor(autor);
        Livro livroAtualizado = livroRepository.save(livro);
        System.out.println("Livro registrado: " + livroAtualizado);

    }

    @Test
    void salvarUsandoCascade(){
        Livro livro = new Livro();
        livro.setIsbn("15452-2142");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setTitulo("Segundo Livro");
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setDataPublicacao(LocalDate.of(1980,2,11));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2008, 3, 16));
        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
    void salvarSemCascade() {
        Livro livro = new Livro();
        livro.setIsbn("15452-2142");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setTitulo("Terceiro Livro");
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setDataPublicacao(LocalDate.of(1980, 2, 11));

        Autor autor = new Autor();
        autor.setNome("Neuma");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2008, 3, 16));
        autorRepository.save(autor);

        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
    void atualizarAutorDeLivro(){

            UUID id = UUID.fromString("a4cadd4b-2445-4b4f-85b9-2789dee4d514");
            var atualizacaoLivro = livroRepository.findById(id).orElse(null);

            UUID idAutor = UUID.fromString("fd6b2298-1311-4b41-945f-ca52e14b6f6b");
            var Eduardo = autorRepository.findById(idAutor).orElse(null);
            if(atualizacaoLivro != null) {
                atualizacaoLivro.setAutor(Eduardo);
                atualizacaoLivro.setTitulo("Livro dá vida");
            livroRepository.save(atualizacaoLivro);
    }
    }

    @Test
    void deleteById(){
        UUID id = UUID.fromString("b9062da7-1488-404f-a852-8e1641db771e");
        livroRepository.deleteById(id);
    }

    @Test
    void deleteCascate(){
        UUID id = UUID.fromString("13b02710-3c91-4d0a-9367-5f426e6a2dba");
        livroRepository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("e3207d4c-0ec5-4a2f-975e-77a56be70519");
        Livro livro = livroRepository.findById(id).orElse(null);

        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisarPeloTituloTest(){
        List<Livro> lista = livroRepository.findByTitulo("fique rico ou morra tentando");
        lista.forEach(System.out::println);

    }

    @Test
    void pesquisarPelaIsbnTest(){
        Optional<Livro> livro = livroRepository.findByIsbn("1542-2215");
        livro.ifPresent(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest(){
        List<Livro> lista = livroRepository.findByTituloAndPreco("fique rico ou morra tentando", BigDecimal.valueOf(170.00));
        lista.forEach(System.out::println);
    }

    @Test
    void ordenarPorTituloAndPrecoJPQL(){
       var list = livroRepository.listarTodosOrdenadoPorTituloAndPreco();
        list.forEach(System.out::println);
    }
    @Test
    void buscarAutorDosLivros(){
        List<Autor> list = livroRepository.listarAutoresDosLivros();
        list.forEach(System.out::println);
    }
    @Test
    void buscarPorTitulosDiferentesDosLivros(){
        var list = livroRepository.listarTituloDiferentes();
        list.forEach(System.out::println);
    }
    @Test
    void buscarPorGeneroEAutoresBrasileiros(){
        var list = livroRepository.listarGeneroAutoresBrasileiros();
        list.forEach(System.out::println);
    }
    @Test
    void buscarPorGeneroTest(){
        var list = livroRepository.findByGenero(GeneroLivro.MISTERIO, "dataPublicacao");
        list.forEach(System.out::println);
    }

    @Test
    void buscarPorGeneroParametrosTest(){
        var list = livroRepository.findByGeneroParameters(GeneroLivro.MISTERIO, "dataPublicacao");
        list.forEach(System.out::println);
    }

    @Test
    void deleterPorGenero(){
        livroRepository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updateDataPublicacao(){
        livroRepository.updateDataPublicacao(LocalDate.of(2001,1,1), BigDecimal.valueOf(140));
    }
}