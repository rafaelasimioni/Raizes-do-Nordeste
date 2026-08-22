package br.com.rafaelasimioni.raizesdonordeste.domain.usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "usuario")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 225)
    private String nome;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist(){
        this.dataCriacao = LocalDateTime.now();
    }
}
