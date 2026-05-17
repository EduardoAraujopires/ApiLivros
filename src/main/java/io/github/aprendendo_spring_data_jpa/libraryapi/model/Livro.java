package io.github.aprendendo_spring_data_jpa.libraryapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
@ToString(exclude = {"autor"})
@EntityListeners(AuditingEntityListener.class)
public class Livro {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", nullable = false, length = 70)
    private String isbn;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false, length = 30)
    private GeneroLivro genero;

    @ManyToOne( fetch = FetchType.LAZY
          //  cascade = CascadeType.ALL
    )
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @CreatedDate
    @Column(name = "data_cadastro") // com essa anotação sempre que tiver uma nova data será atribuida a essa "dataCadastro"
    private LocalDateTime dataCadastro;

    @LastModifiedDate // com essa anotação sempre que tiver uma nova atualização será atribuido a essa "dataAtualizacao"
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;
}
