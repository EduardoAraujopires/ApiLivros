package io.github.aprendendo_spring_data_jpa.libraryapi.repository.specs;

import io.github.aprendendo_spring_data_jpa.libraryapi.model.GeneroLivro;
import io.github.aprendendo_spring_data_jpa.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

 public class LivroSpecs {
    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, cb) ->
                cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike (String titulo) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero){
        return (root, query, cb) ->
                cb.equal(root.get("genero"), genero);
    }

     public static Specification<Livro> anoPublicacaoEqual(Integer ano){
        //select to_char(data_publicacao, 'YYYY') from livro
        // and to_char(data_publicacao, 'YYYY') = :dataPublicacao
         return (root, query, cb) ->
                 cb.equal(cb.function("to_char",
                         String.class, root.get("dataPublicacao")
                         , cb.literal("YYYY")), ano.toString());
     }
}
