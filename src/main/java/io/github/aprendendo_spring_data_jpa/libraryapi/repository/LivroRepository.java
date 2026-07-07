package io.github.aprendendo_spring_data_jpa.libraryapi.repository;

import io.github.aprendendo_spring_data_jpa.libraryapi.model.Autor;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.GeneroLivro;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
  * @see LivroRepositoryTest
 **/

public interface LivroRepository extends JpaRepository<Livro, UUID> , JpaSpecificationExecutor<Livro> {

    // Query method
    // select * from livros where id_livros = id
    List<Livro> findByAutor(Autor autor);

    //select * from livro where titulo = titulo;
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = isbn;
    Optional<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    /**
     * select a.*
     * from livro l
     * join autor a on a.id = l.id_autor
     */
    @Query(" select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    @Query(" select distinct l.titulo from Livro l")
    List<String> listarTituloDiferentes();

    @Query("""
           select l.genero
           from Livro l
           join l.autor a
           where a.nacionalidade = 'brasileira'
           order by l.genero
           """)
    List<String> listarGeneroAutoresBrasileiros();

    //named parameters -> parametros nomeados
    @Query(" select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro, @Param("paramOrdenacao") String nomePropriedade);

    @Query(" select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroParameters(GeneroLivro generoLivro, String nomePropriedade);


    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro livro);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1 where preco = ?2")
    void updateDataPublicacao(LocalDate dataPublicacao, BigDecimal preco);

    boolean existsByAutor(Autor autor);
}
