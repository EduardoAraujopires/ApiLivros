package io.github.aprendendo_spring_data_jpa.libraryapi.model;


import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Type(ListArrayType.class)
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles;

}
