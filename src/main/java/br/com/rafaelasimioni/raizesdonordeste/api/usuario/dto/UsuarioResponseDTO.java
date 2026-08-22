package br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto;

import br.com.rafaelasimioni.raizesdonordeste.domain.usuario.PerfilUsuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private PerfilUsuario perfil;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
}
